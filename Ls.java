/**
 * Created by hujian on 2016/8/22.
 * in this class,i will implement the "ls" function just like unix shell
 * the notices is:
 * 1¡¢you can use the class by alone.
 * 2¡¢you can choose to offer the filepath
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ls {
    //the default path,if not find the offer path,use the default path
    private String path="";
    private static String pathing="";
    public Ls(String Path){
        this.path=Path;
        pathing=Path;
    }
    /**
     * get the file list under the path.
     * @param path the dir you need to look up
     * @return the file name's list under the path
     */
    public static List<String> getFileList(String path) {
        List<String> listFile = new ArrayList<>();
        File dirFile = new File(path);
        //this is a file or not
        if (dirFile.isDirectory()) {
            File[] files = dirFile.listFiles();
            if (null != files && files.length > 0) {
                for (File file : files) {
                    //this is a file,just add
                    if (!file.isDirectory()) {
                        listFile.add(file.getAbsolutePath());
                    } else {
                        //this is a dir,lookup the dir again
                        listFile.addAll(getFileList(file.getAbsolutePath()));
                    }
                }
            }
        }
        return listFile;
    }
    /**
    * fake the unix shell "cd"
    * @param nextPath the goto path
    * @return the goto path name(String)
    * */
    public static String getGoToPathName(String nextPath){
        if(nextPath==null){
            return ".";//return the current path
        }else if(nextPath==".."){
            return new File((new File(pathing)).getAbsolutePath()).getParent();
        }else if(nextPath=="."){
            return (new File(pathing)).getAbsolutePath().toString();
        }else{
            return nextPath;
        }
    }
    /**
     * cd
     * @param path the goto path
     */
    public static void cd(String path){
        pathing=getGoToPathName(path);
    }
    /**
    * rename
    * @param  renameTo the to name
    * */
    public static void rename(String renameTo){
        //re-name,then delete the old
        (new File(pathing)).renameTo(new File(renameTo));
        (new File(pathing)).delete();
        pathing=renameTo;
    }
    /**
     * cat
     * @param  filefrom the from file
     * @param  fileto   the to file
    */
    public static void cat(String filefrom,String fileto){
        //open,then check
        File from=new File((new File(filefrom).getAbsolutePath()));
        File to=new File((new File(fileto).getAbsolutePath()));
        if(from==null||to==null){
            try {
                throw new Exception("the file is empty!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        BufferedReader fromReader=null;
        BufferedWriter toWriter=null;
        //else,start to append the src file to dst file
        try {
            fromReader=new BufferedReader(new FileReader(from));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            toWriter=new BufferedWriter(new FileWriter(to));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line="";
        try {
            while((line=fromReader.readLine())!=null){
                toWriter.append(line);
                toWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //close the file
        try {
            fromReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            toWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getCurrentPath(){
        return (new File(pathing)).getAbsolutePath();
    }
    public static void main(String[] args){
        /*
        Ls ls=new Ls("");
        System.out.println(ls.getCurrentPath());
        //ls.cd("..");
        System.out.println(ls.getCurrentPath());
        //ls.cd("..");
        System.out.println(ls.getCurrentPath());
        (new File("outMe")).renameTo(new File("outYou"));
        (new File("outMe")).delete();
        ls.cd("outYou");
        System.out.println(ls.getCurrentPath());
        Ls.rename("hujianMe");
        System.out.println(ls.getCurrentPath());
        Ls.cat("src/Ls.java","hujianMe/libai.txt");
        */
        String fileName="";
        Pattern pattern=Pattern.compile("[0-9]*");
        Matcher matcher=null;
        for(String name:getFileList(".")){
            if(name.endsWith(".cpp")){
                //get the name
                fileName=name.substring(name.length()-8,name.length()-4);
                matcher=pattern.matcher(fileName);
                if(matcher.matches()) {
                    //System.out.println(fileName);
                    //cat
                    (new File(name)).renameTo(new File("poj"+fileName+".txt"));
                }
            }
        }
    }
}
















