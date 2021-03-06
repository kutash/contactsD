package command;

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

    CANCEL{
        {
            this.command = new CancelCommand();
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

    GETATTACH{
        {
            this.command = new GetAttachCommand();
        }
    },

    SENDEMAIL{
        {
            this.command = new SendEmailCommand();
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
