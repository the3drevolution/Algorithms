import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class HelperMethod {
	private static int totalchars = 0, offset = 0;
	private static InputStream stream;
	private static byte[] buffer = new byte[1024];

	private static int readByte() {
		if (totalchars < 0)
			return 0;
		if (offset >= totalchars) {
			offset = 0;
			try {
				totalchars = stream.read(buffer);
			} catch (IOException e) {
				return 0;
			}
			if (totalchars <= 0)
				return -1;
		}
		return buffer[offset++];
	}

	private static int readInt() {
		int number = readByte();

		while (eolchar(number))
			number = readByte();

		int sign = 1;
		int val = 0;

		if (number == '-') {
			sign = -1;
			number = readByte();
		}

		do {
			if ((number < '0') || (number > '9'))
				return 0;
			val *= 10;
			val += (number - '0');
			number = readByte();
		} while (!eolchar(number));

		return sign * val;
	}

	private static long readLong() {
		int number = readByte();

		while (eolchar(number))
			number = readByte();

		int sign = 1;
		long val = 0;

		if (number == '-') {
			sign = -1;
			number = readByte();
		}

		do {
			if ((number < '0') || (number > '9')) {
				// return sign*val;
				return 0;
			}
			val *= 10;
			val += (number - '0');
			number = readByte();
		} while (!eolchar(number));

		return sign * val;
	}

	private static boolean eolchar(int c) {
		return c == ' ' || c == '\n' || c == -1 || c == '\r' || c == '\t';
	}

	private static final int W = 0, H = 1;

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		Comparator<int[]> dollComparator = new java.util.Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				int c = o1[W] - o2[W];
				if (c == 0) {
					return o2[H] - o1[H];
				}
				return c;
			}
		};
		int tests = readInt();
		while (tests-- > 0) {
			ArrayList<int[]> temp = new ArrayList<int[]>();
			int n = readInt();
			int[][] dolls = new int[n][2];
			for (int i = 0; i < n; ++i) {
				dolls[i][W] = readInt();
				dolls[i][H] = readInt();
			}
			Arrays.sort(dolls, dollComparator);
			temp.add(new int[] { dolls[0][W], dolls[0][H] });
			for (int i = 1; i < n; ++i) {
				int binChoice = -1;
				for (int j = 0, jLen = temp.size(); j < jLen; ++j) {
					int[] currentBin = temp.get(j);
					if (dolls[i][H] > currentBin[H] && dolls[i][W] > currentBin[W]) {
						binChoice = j;
						break;
					}
				}
				if (binChoice == -1) {
					temp.add(dolls[i]);
				} else {
					temp.set(binChoice, dolls[i]);
				}
			}
			pw.println(temp.size());
		}

		pw.flush();
		pw.close();
	}

}