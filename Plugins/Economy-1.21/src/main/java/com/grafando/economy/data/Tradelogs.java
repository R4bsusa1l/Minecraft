package com.grafando.economy.data;
import java.io.File;

public class Consolidation {

    private File Logfile;
    private File Logfolder;

    public void createDirectory() {
        // Logfolder = new File("C:\\Users\\Pius\\Desktop\\Plugins\\Server\\Files\\TradingLogs");
        if (!Logfolder.exists()) {
            // Logfolder.mkdir();
        }
    }
    //lsijliajlkjslkjd
    public void writeTradeFile(String content) {
    }

    public void writeTransactionFile(String content) {
    }
}
