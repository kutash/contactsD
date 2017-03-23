package command;

/**
 * Created by Galina on 13.03.2017.
 */
public enum CommandEnum {
    SAVE{
        {
            this.command = new SaveCommand();
        }
    },

    SHOW{
        {
            this.command = new ShowCommand();
        }
    },

    EDIT{
        {
            this.command = new EditCommand();
        }
    },

    PHOTO{
        {
            this.command = new PhotoCommand();
        }
    },

    DELETE{
        {
            this.command = new DeleteCommand();
        }
    },

    SEARCH{
        {
            this.command = new SearchCommand();
        }
    },

    ATTACH{
        {
            this.command = new AttachCommand();
        }
    },

    EMAIL{
        {
            this.command = new EmailCommand();
        }
    };



    Command command;
    public Command getCurrentCommand() {
        return command;
    }
}
