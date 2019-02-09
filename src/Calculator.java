import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Calculator {

	public static void main(String[] args) throws Exception {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		fc.setDialogTitle("Please select file: Payment_history\\Orders.csv");
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			calculate(fc.getSelectedFile());
		}
	}

	private static void calculate(File file) throws FileNotFoundException, IOException {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		double amount = 0;
		Map<Double, Integer> map = new HashMap<Double, Integer>();
		while ((line = br.readLine()) != null) {
			String array[] = line.split(";");
			String value = array[4].substring(1, array[4].lastIndexOf("\""));
			amount += Double.parseDouble(value);
			if (map.get(Double.parseDouble(value)) == null) {
				map.put(Double.parseDouble(value), 1);
			} else {
				map.put(Double.parseDouble(value), map.get(Double.parseDouble(value)) + 1);
			}
		}
		br.close();
		SortedSet<Double> keys = new TreeSet<Double>(map.keySet());
		String listToShow = "";
		for (Double key : keys) {
			listToShow += key + "€: " + map.get(key) + "x\n";
		}
		listToShow += "----------\nTotal: " + new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP).doubleValue()
				+ "€";
		JOptionPane.showMessageDialog(null, listToShow);
	}

}
