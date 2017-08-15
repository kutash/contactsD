package command;

import service.ContactServiceFactory;
import util.FileManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ContactService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class PhotoCommand implements Command {

    private Logger logger = LogManager.getLogger(PhotoCommand.class);
    private ContactService contactService = ContactServiceFactory.getContactService();

    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String path;
        String stId = request.getParameter("idContact");
        logger.info("getting photo for contact id {}", stId);
        Properties properties = new Properties();
        HttpSession session = request.getSession();
        try {
            properties.load(PhotoCommand.class.getResourceAsStream("/photo.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        path =(String) session.getAttribute("temp_photo_path");
        if (path==null){
            if (StringUtils.isNotEmpty(stId)) {
                Long idContact = Long.parseLong(stId);
                path = contactService.getPhoto(idContact);
                if (path == null) {
                    String appPath = request.getServletContext().getRealPath("");
                    path = appPath + properties.getProperty("AVATAR");
                }
            } else {
                String appPath = request.getServletContext().getRealPath("");
                path = appPath + properties.getProperty("AVATAR");
            }
        }
        File file = new File(path);
        int buffSize = Integer.parseInt(properties.getProperty("BUFFER_SIZE"));

        response.reset();
        response.setBufferSize(buffSize);
        response.setContentType(properties.getProperty("CONTENT_TYPE"));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "avatar; filename=\"" + file.getName() + "\"");
        new FileManager().unloadFile(file, response, buffSize);
        return null;
    }
}
