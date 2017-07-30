package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileLoader {

    private Logger logger = LogManager.getLogger(FileLoader.class);

    public void unloadFile(File file, HttpServletResponse response, int buffSize){

        OutputStream out = null;
        FileInputStream in = null;
        try{
            out = response.getOutputStream();
            in = new FileInputStream(file);
            byte[] buffer = new byte[buffSize];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new UtilException("Exception while unloading file", e);
        }
        finally {
            try {
                if (out != null && in != null) {
                    out.close();
                    in.close();
                }
            } catch (IOException e) {
                logger.error("Error in closing streams");
            }
        }
    }
}
