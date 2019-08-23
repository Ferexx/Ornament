package Data;

import java.io.*;

public class PlayerSettings {
    File settingsFile;
    int volume=50;
    int res=0;

    public PlayerSettings(File settingsFile) {
        this.settingsFile = settingsFile;
        try {
            BufferedReader br = new BufferedReader(new FileReader(settingsFile));
            String st;
            while((st = br.readLine())!=null) {
                String split[] = st.split("=");
                switch(split[0]) {
                    case "volume":
                        volume = Integer.parseInt(split[1]);
                        break;
                    case "resolution":
                        res = Integer.parseInt(split[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveSettings() {
        try {
            FileWriter fw = new FileWriter(settingsFile, false);
            fw.write("volume=" + volume);
            fw.write("resolution=" + res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
