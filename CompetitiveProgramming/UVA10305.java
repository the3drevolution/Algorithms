import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Ordering Tasks
 * 
 * @author arun
 *
 */
public class UVA10305 {
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

	private static Queue<Integer> result;
	private static ArrayList<Integer>[] adjList;
	private static boolean[] visited;

	private static void solve(int v) {
		ArrayList<Integer> adj = adjList[v];
		visited[v] = true;
		for (int i = 0, iLen = adj.size(); i < iLen; ++i) {
			int av = adj.get(i);
			if (!visited[av]) {
				solve(av);
			}
		}
		result.add(v);
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int n, m;
		while (true) {
			n = readInt();
			m = readInt();
			if (n == 0 && m == 0) {
				break;
			}
			adjList = new ArrayList[n];
			for (int i = 0; i < n; ++i) {
				adjList[i] = new ArrayList<Integer>();
			}
			visited = new boolean[n];
			result = new LinkedList<Integer>();
			for (int i = 0; i < m; ++i) {
				int dependent = readInt() - 1, dependee = readInt() - 1;
				adjList[dependee].add(dependent);
			}
			for (int i = 0; i < n; ++i) {
				if (!visited[i]) {
					solve(i);
				}
			}
			Iterator<Integer> resultIterator = result.iterator();
			pw.print(resultIterator.next() + 1);
			while (resultIterator.hasNext()) {
				pw.print(" " + (resultIterator.next() + 1));
			}
			pw.println();
		}
		pw.flush();
		pw.close();
	}
}
