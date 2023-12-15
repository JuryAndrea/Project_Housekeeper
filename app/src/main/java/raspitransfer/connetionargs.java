package raspitransfer;


public class connetionargs {

    public String user;
    public String host;
    public String pwd;
    public String remoteFilePath;

    public String localDestinationPath;

    public connetionargs(String user, String host, String pwd, String remoteFilePath, String localDestinationPath) {
        this.user = user;
        this.host = host;
        this.pwd = pwd;
        this.remoteFilePath = remoteFilePath;
        this.localDestinationPath = localDestinationPath ;
    }
}
