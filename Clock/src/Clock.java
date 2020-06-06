/**
 * @author NJR10byh、怪兽石团长、愛殇璃
 * @time 2020.6.1
 * */

import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Clock implements Runnable{
    static int index;
    static String str;
    static int Hour,Min,Sec,Day;
    static int setHour,setMin,setSec;
    static int DaoHour,DaoMin,DaoSec;
    static int x=707,y=312;
    static int x1=720,y1=390;
    static int result=3;
    static int r=3;
    static int flag=1;
    private JLabel date;
    private JLabel time;
    static private JLabel on_off;
    public Clock() {
        //初始化图形界面
        final JFrame jf=new JFrame();
        jf.setTitle("闹钟");
        jf.setResizable(false);
        jf.setBounds(x,y,506,456);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel jp = new JPanel(null);

        //日期
        date = new JLabel();
        date.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        date.setBounds(161, 81, 200, 22);
        jp.add(date);

        //时间
        time = new JLabel();
        time.setBounds(145, 120, 230, 59);
        time.setFont(new Font("Arial", Font.BOLD, 55));
        jp.add(time);

        //闹钟开关状态
        on_off=new JLabel("Alarm clock status: Off");
        on_off.setBounds(235, 220, 180, 40);
        on_off.setFont(new Font("Arial", Font.BOLD, 16));
        jp.add(on_off);


        //创建顶部工具栏
        JMenuBar bar = new JMenuBar();
        JMenu select = new JMenu("设置");
        JMenu about = new JMenu("关于");
        about.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Author(jf);
            }
        });
        JMenu readme = new JMenu("使用说明");
        readme.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Method(jf);
            }
        });

        bar.add(select);
        bar.add(about);
        bar.add(readme);

        JMenuItem open=new JMenuItem("打开外部文件");
        open.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                show(jf);
            }
        });
        JMenuItem save = new JMenuItem("保存设置");
        JMenuItem exit = new JMenuItem("退出");
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.exit(0);
            }
        });
        select.add(open);
        select.add(save);
        select.add(exit);

        JLabel lb = new JLabel();
        lb.setIcon(new ImageIcon("time.jpg"));
        lb.setBounds(0, 0, 500, 400);
        jp.add(lb);


        JButton btn10 = new JButton("添加闹钟");
        btn10.setBounds(220, 260, 95, 33);
        jp.add(btn10);
        btn10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditClock(jf);
            }
        });
        JButton btn11 = new JButton("取消闹钟");
        btn11.setBounds(330, 260, 95, 33);
        jp.add(btn11);
        btn11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result=JOptionPane.showConfirmDialog(
                        jf,
                        "您希望取消闹钟吗?",
                        "提示",
                        JOptionPane.YES_NO_OPTION
                );
                if(result==JOptionPane.YES_OPTION){
                    setHour=0;
                    setMin=0;
                    setSec=0;
                    on_off.setText("Alarm clock status: Off");
                }
            }
        });
        JButton btn12 = new JButton("添加计时器");
        btn12.setBounds(220, 300, 205, 33);
        jp.add(btn12);
        btn12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditTimer(jf);
            }
        });

        jf.setJMenuBar(bar);
        jf.setContentPane(jp);
        jf.setVisible(true);

    }

    /**
     * 用一个线程来更新时间
     * */
    public void run() {
        while(true){
            try{
                date.setText(new SimpleDateFormat("yyyy 年 MM 月 dd 日  EEEE").format(new Date()));
                time.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }catch(Throwable t){
                t.printStackTrace();
            }
        }
    }

    /**
     * Main函数
     * */
    public static void main(String[] args)
            throws
            ClassNotFoundException,
            UnsupportedLookAndFeelException,
            InstantiationException,
            IllegalAccessException
    {
        //使用Windows外观
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        //运行程序之前,用户需要首先阅读使用说明
        JFrame jDialog = new JFrame();
        jDialog.setSize(400, 280);
        jDialog.setResizable(false);  //不能最大化
        jDialog.setLocationRelativeTo(null);
        jDialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jp1=new JPanel(null);

        //强制阅读
        JRadioButton rb=new JRadioButton(" 我已阅读,以上说明我已知晓! (您需要勾选此项以进入闹钟!!!)");
        rb.setFont(new Font("微软雅黑",Font.PLAIN,13));
        rb.setBounds(10,210,395,18);
        jp1.add(rb);


        JTextArea textArea=new JTextArea();
        textArea.setBounds(0,0,392,200);
        textArea.setLineWrap(true);
        textArea.setText(
                "此小闹钟主面板有三个按钮: 添加闹钟、取消闹钟 和 添加计时器," +
                        "如果运行时,未出现三个按钮，请在添加按钮附近用鼠标轻扫一下。");
        textArea.setFont(new Font("微软雅黑",Font.BOLD,25));
        jp1.add(textArea);
        jDialog.setContentPane(jp1);
        jDialog.setVisible(true);

        /**
         * 阅读完使用说明,并选中我已阅读,才能进入主面板
         * */
        rb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.dispose();
                new Thread(new Clock()).start();
            }
        });
    }


    /**
     * 添加闹钟
     */
    public static void EditClock(JFrame fatherjf) {
        //创建窗口，且设置其为模态窗
        final JDialog jDialog = new JDialog(fatherjf, "设置闹钟", true);
        jDialog.setSize(400, 500);
        jDialog.setResizable(false);  //不能最大化
        jDialog.setLocationRelativeTo(fatherjf);

        Calendar calendar = Calendar.getInstance();
        Hour=calendar.get(Calendar.HOUR);
        Min=calendar.get(Calendar.MINUTE);
        Sec=calendar.get(Calendar.SECOND);

        //时间数据
        String[] hours = new String[]{
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "10", "11", "12"
        };
        String[] minutes = new String[]{
                "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
                "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
                "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"
        };
        String[] seconds = new String[]{
                "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
                "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
                "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"
        };

        //创建下拉框
        final JComboBox box4 = new JComboBox(hours);
        final JComboBox box5 = new JComboBox(minutes);
        final JComboBox box6 = new JComboBox(seconds);

        //下拉框默认选择当前时间
        box4.setSelectedIndex(Hour);
        box5.setSelectedIndex(Min);
        box6.setSelectedIndex(Sec);

        //创建盒子
        Box hBox1 = Box.createHorizontalBox();//设定时间栏
        Box hBox2 = Box.createHorizontalBox();//重新设定的btn
        Box hBox3 = Box.createHorizontalBox();//时分秒
        Box hBox4 = Box.createHorizontalBox();//最底下两个按钮
        Box hBox5 = Box.createHorizontalBox();//添加音乐
        Box hBox6 = Box.createHorizontalBox();//顶部图片
        Box hBox7 = Box.createHorizontalBox();//修改顶部图片
        Box vBox1 = Box.createVerticalBox();

        /**
         * 设置顶部图片
         * */
        final JLabel Header=new JLabel();
        if(index==0){
            Header.setIcon(new ImageIcon("clock1.jpg"));
            Header.setSize(400,150);
        }
        else if(index==1){
            Header.setIcon(new ImageIcon("clock2.jpg"));
            Header.setSize(400,150);
        }
        else if(index==2){
            Header.setIcon(new ImageIcon("clock3.jpg"));
            Header.setSize(400,150);
        }
        else if(index==3){
            Header.setIcon(new ImageIcon("clock4.jpg"));
            Header.setSize(400,150);
        }
        else if(index==4){
            Header.setIcon(new ImageIcon("clock5.jpg"));
            Header.setSize(400,150);
        }
        else if(index==5){
            Header.setIcon(new ImageIcon("clock6.jpg"));
            Header.setSize(400,150);
        }
        Header.setSize(400,150);
        hBox6.add(Header);

        /**
         * 设定时间显示栏
         * */
        JLabel label = new JLabel("上次设定的时间为: ");
        final JTextField textField = new JTextField(str);
        textField.setPreferredSize(new Dimension(100, 50));
        hBox1.add(Box.createHorizontalStrut(100));
        hBox1.add(label);
        hBox1.add(Box.createHorizontalStrut(10));
        hBox1.add(textField);
        hBox1.add(Box.createHorizontalStrut(100));

        /**
         * 再次设定btn
         * */
        JButton btna=new JButton("再次设定");
        JButton btnb=new JButton("重新设定");
        btna.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result=JOptionPane.showConfirmDialog(
                        jDialog,
                        "您希望明天同一时刻再次响铃吗?",
                        "提示",
                        JOptionPane.YES_NO_OPTION
                );
                if(result==JOptionPane.YES_OPTION){
                    Day+=1;  //闹钟延长一天
                }
            }
        });
        btnb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                str=new String();
                textField.setText(str);
            }
        });
        hBox2.add(btna);
        hBox2.add(Box.createHorizontalStrut(30));
        hBox2.add(btnb);




        //时
        box4.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    //System.out.println("选中了"+box4.getSelectedItem());
                    textField.setText("   " +
                            box4.getSelectedItem() + ":" +
                            box5.getSelectedItem() + ":" +
                            box6.getSelectedItem()
                    );
                    str=textField.getText();
                    setHour=box4.getSelectedIndex();
                    setMin=box5.getSelectedIndex();
                    setSec=box6.getSelectedIndex();
                }
            }
        });

        //分
        box5.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    //System.out.println("选中了"+box5.getSelectedItem());
                    textField.setText("   " +
                            box4.getSelectedItem() + ":" +
                            box5.getSelectedItem() + ":" +
                            box6.getSelectedItem()
                    );
                    setHour=box4.getSelectedIndex();
                    setMin=box5.getSelectedIndex();
                    setSec=box6.getSelectedIndex();
                    str=textField.getText();
                }
            }
        });

        //秒
        box6.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    //System.out.println("选中了"+box6.getSelectedItem());
                    textField.setText("   " +
                            box4.getSelectedItem() + ":" +
                            box5.getSelectedItem() + ":" +
                            box6.getSelectedItem()
                    );
                    setHour=box4.getSelectedIndex();
                    setMin=box5.getSelectedIndex();
                    setSec=box6.getSelectedIndex();
                    str=textField.getText();
                }
            }
        });

        //添加标签
        JLabel lbl4 = new JLabel("时: ");
        JLabel lbl5 = new JLabel("分: ");
        JLabel lbl6 = new JLabel("秒: ");

        /**
         * 添加音乐选项盒子
         * */
        JLabel lblmusic = new JLabel("添加音乐: ");
        JTextField textFieldmusic = new JTextField();
        JButton btnmusic = new JButton("浏览...");
        //添加 btnmusic 监听事件,用于打开文件,选择音乐
        btnmusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMusic(jDialog);
            }
        });
        hBox5.add(Box.createHorizontalStrut(88));
        hBox5.add(lblmusic);
        hBox5.add(textFieldmusic);
        hBox5.add(Box.createHorizontalStrut(10));
        hBox5.add(btnmusic);
        hBox5.add(Box.createHorizontalStrut(90));

        /**
         * 更换顶部图片
         * */
        String[] Wallpaper=new String[]{"clock1","clock2","clock3","clock4","clock5","clock6"};
        JLabel wallpaper=new JLabel("更改顶部图片: ");
        JComboBox wallpaper1=new JComboBox(Wallpaper);
        wallpaper1.setSelectedIndex(index);
        wallpaper1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    index=wallpaper1.getSelectedIndex();
                    if(index==0){
                        Header.setIcon(new ImageIcon("clock1.jpg"));
                        Header.setSize(400,150);
                    }
                    else if(index==1){
                        Header.setIcon(new ImageIcon("clock2.jpg"));
                        Header.setSize(400,150);
                    }
                    else if(index==2){
                        Header.setIcon(new ImageIcon("clock3.jpg"));
                        Header.setSize(400,150);
                    }
                    else if(index==3){
                        Header.setIcon(new ImageIcon("clock4.jpg"));
                        Header.setSize(400,150);
                    }
                    else if(index==4){
                        Header.setIcon(new ImageIcon("clock5.jpg"));
                        Header.setSize(400,150);
                    }
                    else if(index==5){
                        Header.setIcon(new ImageIcon("clock6.jpg"));
                        Header.setSize(400,150);
                    }
                }
            }
        });
        hBox7.add(Box.createHorizontalStrut(88));
        hBox7.add(wallpaper);
        hBox7.add(Box.createHorizontalStrut(8));
        hBox7.add(wallpaper1);
        hBox7.add(Box.createHorizontalStrut(88));

        /**
         * 添加按钮监听事件
         * */
        JButton btn1 = new JButton("设定");
        JButton btn2 = new JButton("取消");
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int Result=JOptionPane.showConfirmDialog(
                        jDialog,
                        "保存这个闹钟嘛?",
                        "提示",
                        JOptionPane.YES_NO_OPTION
                );
                if(Result==JOptionPane.YES_OPTION){
                    on_off.setText("Alarm clock status: On");
                    jDialog.setVisible(false);
                    result=3;
                    new Thread() {
                        public void run() {
                            while (true) {
                                try {
                                    Thread.sleep(20);
                                    Calendar calendar = Calendar.getInstance();
                                    Hour=calendar.get(Calendar.HOUR);
                                    Min=calendar.get(Calendar.MINUTE);
                                    Sec=calendar.get(Calendar.SECOND);
                                    //System.out.println(setSec);
                                    if ((setSec == Sec)&&(setMin==Min)&&(setHour==Hour)) {

                                        //利用线程实现窗口抖动
                                        new Thread("Shake"){
                                            public void run () {
                                                while ((result !=0)&&(result !=1)) {
                                                    try {
                                                        Thread.sleep(10);
                                                        x += 10;
                                                        fatherjf.setBounds(x, y, 506, 456);
                                                        y -= 10;
                                                        fatherjf.setBounds(x, y, 506, 456);
                                                        x -= 10;
                                                        fatherjf.setBounds(x, y, 506, 456);
                                                        y += 10;
                                                        fatherjf.setBounds(x, y, 506, 456);
                                                        x += 10;
                                                        fatherjf.setBounds(x, y, 506, 456);
                                                        y -= 10;
                                                        fatherjf.setBounds(x, y, 506, 456);
                                                        x -= 10;
                                                        fatherjf.setBounds(x, y, 506, 456);
                                                        y += 10;
                                                        fatherjf.setBounds(x, y, 506, 456);
                                                        x += 10;
                                                        fatherjf.setBounds(x, y, 506, 456);
                                                        y -= 10;
                                                        fatherjf.setBounds(x, y, 506, 456);
                                                        x -= 10;
                                                        fatherjf.setBounds(x, y, 506, 456);
                                                        y += 10;
                                                        fatherjf.setBounds(x, y, 506, 456);
                                                        x += 10;
                                                        fatherjf.setBounds(x, y, 506, 456);
                                                        y -= 10;
                                                        fatherjf.setBounds(x, y, 506, 456);
                                                        x -= 10;
                                                        fatherjf.setBounds(x, y, 506, 456);
                                                        y += 10;
                                                        fatherjf.setBounds(x, y, 506, 456);
                                                    } catch (InterruptedException interruptedException) {
                                                        interruptedException.printStackTrace();
                                                    }
                                                }
                                            }
                                        }.start();
                                        result=JOptionPane.showConfirmDialog(
                                                jDialog,
                                                "时间到啦!!!   还要接着睡嘛????",
                                                "提醒",
                                                JOptionPane.YES_NO_OPTION
                                        );
                                        if(result==JOptionPane.YES_OPTION){
                                            Sleep(fatherjf);
                                        }
                                        if(result==JOptionPane.NO_OPTION){
                                            on_off.setText("Alarm clock status: Off");
                                        }
                                    }
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }
                            }
                        }
                    }.start();
                }
            }
        });
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                on_off.setText("Alarm clock status: Off");
                jDialog.dispose();
            }
        });

        //所有组件打包进盒子
        hBox3.add(Box.createHorizontalStrut(85));
        hBox3.add(lbl4);
        hBox3.add(box4);
        hBox3.add(Box.createHorizontalStrut(19));
        hBox3.add(lbl5);
        hBox3.add(box5);
        hBox3.add(Box.createHorizontalStrut(19));
        hBox3.add(lbl6);
        hBox3.add(box6);
        hBox3.add(Box.createHorizontalStrut(130));
        hBox4.add(btn1);
        hBox4.add(Box.createHorizontalStrut(50));
        hBox4.add(btn2);

        vBox1.add(hBox6);//顶部图片
        vBox1.add(Box.createVerticalStrut(15));
        vBox1.add(hBox1);//设定时间
        vBox1.add(Box.createVerticalStrut(25));
        vBox1.add(hBox2);
        vBox1.add(Box.createVerticalStrut(20));
        vBox1.add(hBox3);//时分秒
        vBox1.add(Box.createVerticalStrut(30));
        vBox1.add(hBox5);//添加音乐
        vBox1.add(Box.createVerticalStrut(30));
        vBox1.add(hBox7);//更改顶部图片
        vBox1.add(Box.createVerticalStrut(60));
        vBox1.add(hBox4);//两个按钮
        vBox1.add(Box.createVerticalStrut(5));

        jDialog.setContentPane(vBox1);
        jDialog.setVisible(true);
    }

    /**
     * 关于弹出的信息
     **/
    public static void Author(JFrame fatherjf){
        final JDialog jDialog = new JDialog(fatherjf, "闹钟v1.0", true);
        jDialog.setSize(230, 180);
        jDialog.setResizable(false);  //不能最大化
        jDialog.setLocationRelativeTo(fatherjf);

        Box hbox1=Box.createHorizontalBox();//作者
        Box hbox2=Box.createHorizontalBox();
        Box hbox3=Box.createHorizontalBox();
        Box hbox4=Box.createHorizontalBox();//创作时间
        Box hbox5=Box.createHorizontalBox();//按钮
        Box vbox=Box.createVerticalBox();
        JLabel lb1=new JLabel("作者：");
        lb1.setFont(new Font("微软雅黑",Font.BOLD, 12));
        JLabel lb2=new JLabel("B18030313-王忠琦");
        hbox1.add(lb1);
        hbox1.add(Box.createHorizontalStrut(6));
        hbox1.add(lb2);
        JLabel lb3=new JLabel("B18030315-石磊");
        hbox2.add(lb3);
        JLabel lb4=new JLabel("B18030316-包宇豪");
        hbox3.add(lb4);
        JLabel lb5=new JLabel("创作时间: ");
        JLabel lb6=new JLabel("2020.6.1");
        lb5.setFont(new Font("微软雅黑",Font.BOLD, 12));
        hbox4.add(lb5);
        hbox4.add(Box.createHorizontalStrut(8));
        hbox4.add(lb6);
        JButton btn=new JButton("确定");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.setVisible(false);
            }
        });
        hbox5.add(btn);

        vbox.add(Box.createVerticalStrut(25));
        vbox.add(hbox1);
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(hbox2);
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(hbox3);
        vbox.add(Box.createVerticalStrut(5));
        vbox.add(hbox4);
        vbox.add(Box.createVerticalStrut(12));
        vbox.add(hbox5);
        vbox.add(Box.createVerticalStrut(2));

        jDialog.setContentPane(vbox);
        jDialog.setVisible(true);
    }

    /**
     * 使用说明
     * */
    public static void Method(JFrame fatherjf){
        final JDialog jDialog = new JDialog(fatherjf, "使用说明", true);
        jDialog.setSize(315, 150);
        jDialog.setResizable(true);  //不能最大化
        jDialog.setLocationRelativeTo(fatherjf);
        JPanel jp=new JPanel(null);

        JTextArea textArea=new JTextArea();
        textArea.setBounds(0,0,300,150);
        textArea.setLineWrap(true);
        textArea.setText(
                "此小闹钟主面板有三个按钮: 添加闹钟、取消闹钟 和 添加计时器," +
                "如果运行时,未出现三个按钮，请在添加按钮附近用鼠标轻扫一下。");
        textArea.setFont(new Font("微软雅黑",Font.PLAIN,16));
        jp.add(textArea);
        jDialog.setContentPane(jp);
        jDialog.setVisible(true);
    }

    /**
     * 添加计时器
     * */
    public static void EditTimer(JFrame fatherjf){
        final JFrame jf=new JFrame("计时器");
        jf.setBounds(x1,y1,480,360);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        final JLabel lb1=new JLabel();
        lb1.setFont(new Font("Arial", Font.BOLD, 60));
        lb1.setText("0:0:0");
        JLabel lb2=new JLabel("添加倒计时: ");
        lb2.setFont(new Font("宋体",Font.BOLD,20));
        Box hbox=Box.createHorizontalBox(); //时间显示
        hbox.add(lb1);

        //时间数据
        String[] hours = new String[]{
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "10", "11", "12"
        };
        String[] minutes = new String[]{
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
                "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
                "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"
        };
        String[] seconds = new String[]{
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
                "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
                "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"
        };

        JLabel lbl1 = new JLabel("时: ");
        JLabel lbl2 = new JLabel("分: ");
        JLabel lbl3 = new JLabel("秒: ");

        //创建下拉框
        final JComboBox box1 = new JComboBox(hours);//时
        final JComboBox box2 = new JComboBox(minutes);//分
        final JComboBox box3 = new JComboBox(seconds);//秒

        //时
        box1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    lb1.setText(
                            box1.getSelectedItem() + ":" +
                            box2.getSelectedItem() + ":" +
                            box3.getSelectedItem()
                    );
                    flag=1;
                    DaoHour=box1.getSelectedIndex();
                    DaoMin=box2.getSelectedIndex();
                    DaoSec=box3.getSelectedIndex();
                }
            }
        });

        //分
        box2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    lb1.setText(
                            box1.getSelectedItem() + ":" +
                            box2.getSelectedItem() + ":" +
                            box3.getSelectedItem()
                    );
                    flag=1;
                    DaoHour=box1.getSelectedIndex();
                    DaoMin=box2.getSelectedIndex();
                    DaoSec=box3.getSelectedIndex();
                }
            }
        });

        //秒
        box3.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    lb1.setText(
                            box1.getSelectedItem() + ":" +
                            box2.getSelectedItem() + ":" +
                            box3.getSelectedItem()
                    );
                    flag=1;
                    DaoHour=box1.getSelectedIndex();
                    DaoMin=box2.getSelectedIndex();
                    DaoSec=box3.getSelectedIndex();
                }
            }
        });

        Box hbox0=Box.createHorizontalBox(); //lb2
        Box hbox1=Box.createHorizontalBox(); //时、分、秒
        Box hbox2=Box.createHorizontalBox();//两个Button
        Box vbox=Box.createVerticalBox(); //竖直方向盒子

        JButton btn1=new JButton("设定");
        JButton btn2=new JButton("取消");

        /**
         * 倒计时功能
         * */
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                r=3;
                flag=1;
                new Thread(){
                    @Override
                    public void run() {
                        //System.out.println(flag);
                        while ((r != 0) && (r != 1)&& flag==1) {
                            try {
                                Thread.sleep(1000);
                                //System.out.println(flag);
                                if (DaoSec != -1) {
                                    DaoSec--;
                                    lb1.setText(DaoHour + ":" + DaoMin + ":" + DaoSec);
                                }
                                if(DaoHour==0&&DaoMin!=0&&DaoSec==-1){
                                    DaoMin--;
                                    DaoSec=59;
                                    lb1.setText(DaoHour + ":" + DaoMin + ":" + DaoSec);
                                }
                                if(DaoHour!=0&&DaoMin!=0&&DaoSec==-1){
                                    DaoMin--;
                                    DaoSec=59;
                                    lb1.setText(DaoHour + ":" + DaoMin + ":" + DaoSec);
                                }
                                if(DaoHour!=0&&DaoMin==0&&DaoSec==-1){
                                    DaoMin=59;
                                    DaoSec=59;
                                    lb1.setText(DaoHour + ":" + DaoMin + ":" + DaoSec);
                                }
                                if(DaoHour!=0&&DaoMin==0&&DaoSec==0){
                                    DaoHour--;
                                    DaoMin=59;
                                    DaoSec=59;
                                }
                                if ((DaoHour==0)&&(DaoMin==0)&&(DaoSec==0)) {

                                    //利用线程实现窗口抖动
                                    new Thread("Shake") {
                                        public void run() {
                                            while ((r != 0) && (r != 1)) {
                                                try {
                                                    Thread.sleep(10);
                                                    x1 += 10;
                                                    jf.setBounds(x1, y1, 480, 360);
                                                    y1 -= 10;
                                                    jf.setBounds(x1, y1, 480, 360);
                                                    x1 -= 10;
                                                    jf.setBounds(x1, y1, 480, 360);
                                                    y1 += 10;
                                                    jf.setBounds(x1, y1, 480, 360);
                                                    x1 += 10;
                                                    jf.setBounds(x1, y1, 480, 360);
                                                    y1 -= 10;
                                                    jf.setBounds(x1, y1, 480, 360);
                                                    x1 -= 10;
                                                    jf.setBounds(x1, y1, 480, 360);
                                                    y1 += 10;
                                                    jf.setBounds(x1, y1, 480, 360);
                                                    x1 += 10;
                                                    jf.setBounds(x1, y1, 480, 360);
                                                    y1 -= 10;
                                                    jf.setBounds(x1, y1, 480, 360);
                                                    x1 -= 10;
                                                    jf.setBounds(x1, y1, 480, 360);
                                                    y1 += 10;
                                                    jf.setBounds(x1, y1, 480, 360);
                                                    x1 += 10;
                                                    jf.setBounds(x1, y1, 480, 360);
                                                    y1 -= 10;
                                                    jf.setBounds(x1, y1, 480, 360);
                                                    x1 -= 10;
                                                    jf.setBounds(x1, y1, 480, 360);
                                                    y1 += 10;
                                                    jf.setBounds(x1, y1, 480, 360);
                                                } catch (InterruptedException interruptedException) {
                                                    interruptedException.printStackTrace();
                                                }
                                            }
                                        }
                                    }.start();
                                       r= JOptionPane.showConfirmDialog(
                                            jf,
                                            "倒计时结束!!",
                                            "提醒",
                                            JOptionPane.YES_NO_OPTION
                                    );
                                }
                                if(r==JOptionPane.YES_OPTION||r==JOptionPane.NO_OPTION){
                                    flag=0;
                                }
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        hbox0.add(lb2);
        hbox1.add(Box.createHorizontalStrut(70));
        hbox1.add(lbl1);
        hbox1.add(Box.createHorizontalStrut(5));
        hbox1.add(box1);
        hbox1.add(Box.createHorizontalStrut(30));
        hbox1.add(lbl2);
        hbox1.add(Box.createHorizontalStrut(5));
        hbox1.add(box2);
        hbox1.add(Box.createHorizontalStrut(30));
        hbox1.add(lbl3);
        hbox1.add(Box.createHorizontalStrut(5));
        hbox1.add(box3);
        hbox1.add(Box.createHorizontalStrut(70));
        hbox2.add(btn1);
        hbox2.add(Box.createHorizontalStrut(20));
        hbox2.add(btn2);


        vbox.add(Box.createVerticalStrut(50));
        vbox.add(hbox);
        vbox.add(Box.createVerticalStrut(20));
        vbox.add(hbox0);
        vbox.add(Box.createVerticalStrut(30));
        vbox.add(hbox1);
        vbox.add(Box.createVerticalStrut(66));
        vbox.add(hbox2);
        vbox.add(Box.createVerticalStrut(2));



        jf.setContentPane(vbox);
        jf.setVisible(true);
    }

    /**
     * 文件选择函数
     * */
    static void showMusic(Component parent) {
        JFileChooser music = new JFileChooser();
        music.setCurrentDirectory(new File("."));//.为当前文件夹
        music.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        music.setMultiSelectionEnabled(false);
        //添加文件过滤器,只允许音乐文件(mp3、flac、ape、wav)
        music.addChoosableFileFilter(new FileNameExtensionFilter("音乐文件(*.mp3,*.flac,*.wav,*.ape)", "mp3", "flac", "ape", "wav"));
        int result = music.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] files = music.getSelectedFiles();
            for (File file : files) {
                System.out.println(file.getName());
            }
        }
    }

    /**
     * 外部文件打开函数
     * */
    static void show(Component parent) {
        JFileChooser file1 = new JFileChooser();
        file1.setCurrentDirectory(new File(".")); //.为当前文件夹
        file1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        file1.setMultiSelectionEnabled(false);
        int result = file1.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] files = file1.getSelectedFiles();
            for (File file : files) {
                System.out.println(file.getName());
            }
        }
    }

    /**
     * 贪睡功能
     * */
    public static void Sleep(JFrame framejf) {
        //创建窗口，且设置其为模态窗
        //final JDialog jDialog1 = new JDialog(fatherjdialog, "贪睡", true);
        final JFrame JF=new JFrame("贪睡");
        JF.setSize(200, 200);
        JF.setResizable(false);  //不能最大化
        JF.setLocationRelativeTo(framejf);

        Box hbox=Box.createHorizontalBox();
        Box hbox1=Box.createHorizontalBox();
        Box hbox2=Box.createHorizontalBox();
        Box hbox3=Box.createHorizontalBox();
        Box vbox=Box.createVerticalBox();

        JButton btntest=new JButton("10秒(测试功能)");
        JButton btn1=new JButton("2分钟");
        JButton btn2=new JButton("10分钟");
        JButton btn3=new JButton("1小时");

        //贪睡10秒(测试功能)
        btntest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSec+=10;
                JF.setVisible(false);
                new Thread() {
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(100);
                                Calendar calendar = Calendar.getInstance();
                                Hour=calendar.get(Calendar.HOUR);
                                Min=calendar.get(Calendar.MINUTE);
                                Sec=calendar.get(Calendar.SECOND);
                                if ((setSec == Sec)&&(setMin==Min)&&(setHour==Hour)) {
                                    int result=JOptionPane.showConfirmDialog(
                                            framejf,
                                            "时间到啦!!!   还要接着睡嘛????",
                                            "提醒",
                                            JOptionPane.YES_NO_OPTION
                                    );
                                    if(result==JOptionPane.YES_OPTION){
                                        Sleep(framejf);
                                    }
                                    if(result==JOptionPane.NO_OPTION){
                                        on_off.setText("Alarm clock status: Off");
                                    }
                                }
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        });

        //贪睡2分钟
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setMin+=2;
                JF.setVisible(false);
                new Thread() {
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(100);
                                Calendar calendar = Calendar.getInstance();
                                Hour=calendar.get(Calendar.HOUR);
                                Min=calendar.get(Calendar.MINUTE);
                                Sec=calendar.get(Calendar.SECOND);
                                if ((setSec == Sec)&&(setMin==Min)&&(setHour==Hour)) {
                                    int result=JOptionPane.showConfirmDialog(
                                            framejf,
                                            "时间到啦!!!   还要接着睡嘛????",
                                            "提醒",
                                            JOptionPane.YES_NO_OPTION
                                    );
                                    if(result==JOptionPane.YES_OPTION){
                                        Sleep(framejf);
                                    }
                                    if(result==JOptionPane.NO_OPTION){
                                        on_off.setText("Alarm clock status: Off");
                                    }
                                }
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        });

        //贪睡10分钟
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setMin+=10;
                JF.setVisible(false);
                new Thread() {
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(100);
                                Calendar calendar = Calendar.getInstance();
                                Hour=calendar.get(Calendar.HOUR);
                                Min=calendar.get(Calendar.MINUTE);
                                Sec=calendar.get(Calendar.SECOND);
                                if ((setSec == Sec)&&(setMin==Min)&&(setHour==Hour)) {
                                    int result=JOptionPane.showConfirmDialog(
                                            framejf,
                                            "时间到啦!!!   还要接着睡嘛????",
                                            "提醒",
                                            JOptionPane.YES_NO_OPTION
                                    );
                                    if(result==JOptionPane.YES_OPTION){
                                        Sleep(framejf);
                                    }
                                    if(result==JOptionPane.NO_OPTION){
                                        on_off.setText("Alarm clock status: Off");
                                    }
                                }
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        });
        //贪睡1小时
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setHour+=1;
                JF.setVisible(false);
                new Thread() {
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(100);
                                Calendar calendar = Calendar.getInstance();
                                Hour=calendar.get(Calendar.HOUR);
                                Min=calendar.get(Calendar.MINUTE);
                                Sec=calendar.get(Calendar.SECOND);
                                if ((setSec == Sec)&&(setMin==Min)&&(setHour==Hour)) {
                                    int result=JOptionPane.showConfirmDialog(
                                            framejf,
                                            "时间到啦!!!   还要接着睡嘛????",
                                            "提醒",
                                            JOptionPane.YES_NO_OPTION
                                    );
                                    if(result==JOptionPane.YES_OPTION){
                                        Sleep(framejf);
                                    }
                                    if(result==JOptionPane.NO_OPTION){
                                        on_off.setText("Alarm clock status: Off");
                                    }
                                }
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        });


        hbox.add(btntest);
        hbox1.add(btn1);
        hbox2.add(btn2);
        hbox3.add(btn3);

        vbox.add(Box.createVerticalStrut(10));
        vbox.add(hbox);
        vbox.add(Box.createVerticalStrut(15));
        vbox.add(hbox1);
        vbox.add(Box.createVerticalStrut(15));
        vbox.add(hbox2);
        vbox.add(Box.createVerticalStrut(15));
        vbox.add(hbox3);

        JF.setContentPane(vbox);
        JF.setVisible(true);
    }

}