package command;

import model.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by Galina on 25.03.2017.
 */
public class GetAttachCommand implements Command{

    private Logger logger = LogManager.getLogger(GetAttachCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String stId = request.getParameter("idContact");
        Long idContact= Long.parseLong(stId);
        logger.info("getting attachments contact id {}", idContact);
        Properties properties = new Properties();
        try {
            properties.load(GetAttachCommand.class.getResourceAsStream("/attachment.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileName = request.getParameter("name");
        if(idContact == null || fileName == null) {
            return null;
        }

        String savePath = properties.getProperty("ATTACH_PATH");
        savePath += File.separator + idContact + File.separator + fileName;
        File file = new File(savePath);
        if(! file.exists()) {
            return null;
        }

        int buffSize = Integer.parseInt(properties.getProperty("BUFFER_SIZE"));

        response.reset();
        response.setBufferSize(buffSize);
        response.setContentType(properties.getProperty("CONTENT_TYPE"));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        OutputStream out = null;
        FileInputStream in = null;
        try {
            out = response.getOutputStream();
            in = new FileInputStream(file);
            byte[] buffer = new byte[buffSize];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
