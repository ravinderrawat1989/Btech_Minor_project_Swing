import  java.awt.*;
import  javax.swing.*;
import  java.awt.event.*;
import  java.io.*;
import  java.lang.reflect.*;

class MyJEditor  extends JFrame implements ActionListener
{  String FType;
   int FStyle,FSize,temp_size=14;
    JTextArea txt;
   JTextArea temptxt;
  File  Tempfile;
     public MyJEditor(String title)
  {  
     super(title);
      Container c=getContentPane();      
   txt=new JTextArea(); 
    
      txt.setBackground(Color.WHITE);    
      txt.setForeground(Color.BLACK);
      c.add(new JScrollPane(txt));
      txt.setFont(new Font("Times New Roman",Font.BOLD,14));
      txt.setDragEnabled(true);
      JMenu file=new JMenu("File"); 
      JMenu Edit=new JMenu("Edit"); 
      JMenu Run= new JMenu("Run");
      JMenu FontStyle=new JMenu("Font Style");
       JMenu FontSize= new JMenu("Font Size");
       JMenu FontType= new JMenu("Font Type");
      JMenu Compile=new JMenu("Compile");                   
    JMenuItem  Compile1=new JMenuItem("Compile2");
    JMenuItem Run1 =new JMenuItem("Run1");  JMenuItem AppletViewer =new JMenuItem("Applet Viewer");
      JMenuItem  i1=new JMenuItem("New");         JMenuItem InternetExplorer =new JMenuItem("Internet Explorer");   
      JMenuItem i2=new JMenuItem("Open");      
      JMenuItem i3 =new JMenuItem("Save As");
      JMenuItem i4 =new JMenuItem("Cut");                                        
      JMenuItem i5=new JMenuItem("Copy");
      JMenuItem  i6=new JMenuItem("Paste");
      JMenuItem  i7=new JMenuItem("Select All");
      JMenuItem i8=new JMenuItem("Bold");
      JMenuItem  i9=new JMenuItem("Italics");
      JMenuItem  i10=new JMenuItem("Increase Font Size");
      JMenuItem  i11=new JMenuItem("Decrease Font Size");
      file.add(i1);file.add(i2);file.add(i3); Compile.add(Compile1);Compile.add(AppletViewer);Compile.add(InternetExplorer);Run.add(Run1);
      Edit.add(i4);Edit.add(i5);Edit.add(i6);Edit.add(i7);FontStyle.add(i8);FontStyle.add(i9);
      FontSize.add(i10);FontSize.add(i11);
      JMenuBar bar=new JMenuBar();
      bar.add(file); bar.add(Edit);bar.add(Compile);bar.add(Run);Compile.setVisible(true);bar.add(FontStyle);
      bar.add(FontSize);bar.add(FontType);
      setJMenuBar(bar);
  
    Compile1.addActionListener(this);AppletViewer.addActionListener(this);InternetExplorer.addActionListener(this);Run1.addActionListener(this);
     i1.addActionListener(this);i2.addActionListener(this);i3.addActionListener(this);
      i4.addActionListener(this);i5.addActionListener(this);i6.addActionListener(this);i7.addActionListener(this);
      i8.addActionListener(this);i9.addActionListener(this);i10.addActionListener(this);
      i11.addActionListener(this);
      setSize(499,299); setDefaultLookAndFeelDecorated(true);
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      String tempstr="";      }

     public void actionPerformed(ActionEvent e)
     { String str=e.getActionCommand();
        if (str.equals("New"))
            { txt.setText(""); }
        if (str.equals("Open"))
         {  JFileChooser jfc   =  new JFileChooser();
             jfc.showOpenDialog(this);
            File   fopen = jfc.getSelectedFile();
            Tempfile=fopen;
            try {  InputStream   in  =  new FileInputStream(fopen);
                   byte arr[] = new byte[in.available()]; 
                   in.read(arr);
                   txt.setText(new String(arr));
                   in.close();}  
             catch(Exception ex){}
       }
       else if(str.equals("Save As"))
         {  JFileChooser jfc=new JFileChooser();
             jfc.showSaveDialog(this);
             File fsave=jfc.getSelectedFile();     
         Tempfile=fsave;
           try{ 
                PrintWriter  out  =  new PrintWriter(new FileOutputStream(fsave));
                out.write(txt.getText());
                 out.close();
                 } catch(Exception ex){}
       }

         else if(str.equals("Copy"))
             {   txt.copy();   }

          else if(str.equals("Cut") )
              { txt.cut();}      
                                             
          else if(str.equals("Paste"))
              {  txt.paste(); } 
            
          else if(str.equals("Select All"))
              { txt.selectAll();}
        else if(str.equals("Compile2"))
              { 
       try{
        String fname=Tempfile.getName();
          if(fname.endsWith(".java")){
       System.out.println("javac "+fname);
      String  cmd="javac "+fname;
          Process child =Runtime.getRuntime().exec(cmd);
         InputStream   in=child.getErrorStream();
          BufferedReader dis=new BufferedReader(new InputStreamReader(in));
          String val;        
          JFrame frm1= new JFrame("Output Window");  
          Container c1=frm1.getContentPane(); 
          temptxt=new JTextArea();
         c1.add(new JScrollPane(temptxt));
       frm1.setVisible(true);frm1.setSize(299,299);
        temptxt.setFont(new Font("Times New Roman",Font.BOLD,18));
       //frm1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
          temptxt.append("\n");
          while((val=dis.readLine())!=null)
         temptxt.append(val+"\n");
          in.close();
           try
            {
               child.waitFor();
            }
          catch(Exception ex){}
          if(child.exitValue()==0)
            { 
             temptxt.setText("\n\n CLASS FILE CREATED");
              
            }
          }}
        catch(Exception ep){System.out.println(ep);temptxt.append("Error Compiling !  Not a valid  Java File   ! ");}
              
}  
   else if(str.equals("Run1")){
String cmd;
FileWriter fw;
BufferedWriter bw  ;
//JScrollPane sp;
JFrame frm1= new JFrame("Output Window");
frm1.setLayout(new BorderLayout());
frm1.setSize(800,200);
frm1.setVisible(true);
JTextArea  etx=new JTextArea(10,10);
etx.setFont(new Font("Times New Roman",Font.BOLD,20));
frm1.add(new JScrollPane(etx),BorderLayout.CENTER);
frm1.reshape(4,260,800,200);
etx.setVisible(true);
String fname=Tempfile.getName();
if(fname.endsWith(".java"))
{ 
   etx.setText("Running..");
   try{
fw=new FileWriter(getTitle());
bw=new BufferedWriter(fw);
bw.write(txt.getText());
bw.close();}
catch(Exception exp){System.out.println("ERROR "+exp);}
try
{     int id=fname.indexOf('.');
      String sr=fname.substring(0,id);
      String cmdr="java"+" "+sr;
      Process child =Runtime.getRuntime().exec(cmdr);
      InputStream in=child.getInputStream();
      BufferedReader dis=new BufferedReader(new InputStreamReader(in));
      String val;
      etx.append("\n");
      while((val=dis.readLine())!=null)
      etx.append(val+"\n");
      in.close();
      try{      child.waitFor();       }
          catch(Exception ex){System.out.print(ex);}
      if(child.exitValue()!=0)
            {
             InputStream in1=child.getErrorStream();
              int re1;

              while((re1=in1.read())!=-1)
              etx.append(String.valueOf((char)re1));
              in1.close();}
          }
        catch(Exception ep){System.out.println(ep);}
         }
        else{etx.setText("Error Compiling !  Not a valid  Java File   ! ");}
                     }                   /*run end here*/

        else if(str.equals("Applet Viewer"))
{         String cmd;
FileWriter fw;
BufferedWriter bw;
JFrame frm2=new JFrame();
frm2.setLayout(new BorderLayout());
JTextArea etx=new JTextArea(10,10);
etx.setVisible(true);
 String fname=Tempfile.getName();
if(fname.endsWith(".java"))
{
etx.setText("Running The Applet Window" );
try{
   String cmd1="appletViewer "+ fname;
   Process child=Runtime.getRuntime().exec(cmd1);
   InputStream in=child.getErrorStream();
   DataInputStream dis=new DataInputStream(in);
   String val;
   while((val=dis.readLine())!= null)
   System.out.println(val+"\n");
   in.close();
   try{ 
     child.waitFor();
}
catch(Exception ex)  {  }
if(child.exitValue()==0)
{
etx.setText("Success class file created");
}

}
catch(Exception ep){System.out.println(ep);}
}
else{etx.setText("Error Compiling ! Not a valid Java File  !");}}


else if(str.equals("Internet Explorer"))
{String fname=Tempfile.getName();
Runtime rn=Runtime.getRuntime();
Process pro=null;
if((fname.endsWith(".html"))||(fname.endsWith(".htm")))
{
try{
/*  pro=rn.exec("C:\Program Files\Internet Explorer\IEXPLORE.EXE"+"  "+getTitle());
*/}catch(Exception exp){    System.out.println("Error :"+exp);}
 }
  else
   {   txt.append("\n\nError in Running");
      txt.append("\nThe File Does not valid for running in the Explorer");}}


        else if(str.equals("Bold"))
               {     
                  txt.setFont(new Font("Times new Roman",Font.BOLD,temp_size)); }
 
        else if(str.equals("Italics"))
               {     
                  txt.setFont(new Font("Times new Roman",Font.ITALIC,temp_size)); }

        else if(str.equals("Increase Font Size"))
               {    temp_size+=2;
                  txt.setFont(new Font("Times new Roman",Font.BOLD,temp_size)); }

        else if(str.equals("Decrease Font Size") && temp_size>=3)
               {    temp_size-=2;
                  txt.setFont(new Font("Times new Roman",Font.BOLD,temp_size)); }

        


   } /*action performed ends here*/

            



public void FChange(String FType , int FStyle, int FSize)
 {     txt.setFont(new Font(FType,FStyle,FSize));}     
  
 public static void main(String arg[])
 {    MyJEditor frm=new MyJEditor("Ravinder Rawat's  EDITOR");}    }
