import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA10360 {
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

	private static boolean eolchar(int c) {
		return c == ' ' || c == '\n' || c == -1 || c == '\r' || c == '\t';
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int testcases = readInt();

		while (testcases-- > 0) {
			int d = readInt();
			int[][] l = new int[1024 + 1][1024 + 1];
			for (int i = 0, iLen = readInt(); i < iLen; ++i) {
				l[readInt()][readInt()] = readInt();
			}
			for (int i = 1; i <= 1024; ++i) {
				for (int j = 1; j <= 1024; ++j) {
					l[i][j] = l[i][j] + l[i][j - 1];
				}
			}
			for (int i = 1; i <= 1024; ++i) {
				for (int j = 1; j <= 1024; ++j) {
					l[i][j] = l[i][j] + l[i - 1][j];
				}
			}
			int max = 0, x = 0, y = 0;
			for (int i = 1; i <= 1024; ++i) {
				for (int j = 1; j <= 1024; ++j) {
					int brx = min(1024, i + d), bry = min(1024, j + d);
					int tlx = max(0, i - d - 1), tly = max(0, j - d - 1);
					int p = l[brx][bry] - l[brx][tly] - l[tlx][bry] + l[tlx][tly];
					if (max < p) {
						max = p;
						x = i;
						y = j;
					}
				}
			}
			pw.println(x + " " + y + " " + max);

		}

		pw.flush();
		pw.close();
	}

	private static int max(int a, int b) {
		return a > b ? a : b;
	}

	private static int min(int a, int b) {
		return a < b ? a : b;
	}
}