package command;

import DAO.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by Galina on 14.03.2017.
 */
public class PhotoCommand implements Command {
    DAO contactDAO = DAOFactory.getContactDao();

    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String path;
        String stId = request.getParameter("idContact");
        Properties properties = new Properties();
        try {
            properties.load(PhotoCommand.class.getResourceAsStream("/photo.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (stId.equals("")){
            String appPath = request.getServletContext().getRealPath("");
            path = appPath + properties.getProperty("AVATAR");
        }else {
            Long idContact = Long.parseLong(stId);
            path = contactDAO.getPhoto(idContact);

            if (path == null) {
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
        response.setHeader("Content-Disposition", "avatar; filename=/" + file.getName() + "/");

        try{
            OutputStream out = response.getOutputStream();
            FileInputStream in = new FileInputStream(file);
            byte[] buffer = new byte[buffSize];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
