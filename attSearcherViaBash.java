import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class attSearcherViaBash {

	public static void main(String[] args) {
		initialize();
    }

	public static void initialize() {
        Map<Integer, File> map = new HashMap<>();

        Scanner in = new Scanner(System.in);

        System.out.print("Enter directory: ");
        String directoryPath = in.nextLine();
        File fileObj = new File(String.valueOf(directoryPath));

        System.out.print("Enter search Attribute: ");
        String searchKey = in.nextLine();

        if (fileObj.exists() && fileObj.isDirectory()) {
            File a[] = fileObj.listFiles();
            for (int i = 0; i < a.length; i++) { map.put(i + 1, a[i]); }

            for (Map.Entry<Integer, File> temp : map.entrySet()) {
                System.out.print(temp.getKey() + ": ");
                System.out.println(temp.getValue().getName());
            }

            System.out.print("Enter the number of file you want to view: ");
            int fileChoice = in.nextInt();
            String path = String.valueOf(map.get(fileChoice));
            System.out.println(convertLogFileToString(path, searchKey));
        }

        in.close();
        System.exit(0);
    }

    public static String convertLogFileToString(String path, String searchID) {
        String out = "";

        try {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();

            String log = stringBuilder.toString();

            String regexSpecified = "\"" + searchID + "\": [\"](.*)[\"],";
            Pattern pattern = Pattern.compile(regexSpecified);
            Matcher matcher = pattern.matcher(log);

            while (matcher.find()) {
                out += matcher.group() + "\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return out;
    }

}