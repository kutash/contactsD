package command;

import util.FileManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class GetAttachCommand implements Command {

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
        if(idContact == 0 || fileName == null) {
            return null;
        }
        String savePath = properties.getProperty("ATTACH_PATH");
        savePath += File.separator + idContact + File.separator + fileName;
        File file = new File(savePath);
        if(!file.exists()) {
            return null;
        }
        int buffSize = Integer.parseInt(properties.getProperty("BUFFER_SIZE"));
        response.reset();
        response.setBufferSize(buffSize);
        response.setContentType(properties.getProperty("CONTENT_TYPE"));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        new FileManager().unloadFile(file, response, buffSize);
        return null;
    }
}
