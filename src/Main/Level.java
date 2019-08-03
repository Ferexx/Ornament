package Main;


import java.io.*;

public class Level {

    File levelFolder, entityFile;
    Handler handler;
    
    public Level(String levelFolder, Handler handler) {
        this.entityFile = new File(levelFolder+"/entities.csv");
        this.handler = handler;
        parseEntities(this.entityFile);
    }


    private void parseEntities(File entitiesFile) {
        String line= "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(entityFile));
            while((line = br.readLine())!=null) {
                String[] values = line.split(",");
                for(int i=0;i<values.length;i++) {

                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
