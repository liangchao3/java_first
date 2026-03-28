import java.util.ArrayList;
import java.util.Scanner;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void main(String[] args) {
        ArrayList<Student> list = new ArrayList<>();

       loop:while (true) {
            System.out.println("-----------欢迎来到学生管理系统-----------");
            System.out.println("1：添加学生");
            System.out.println("2：删除学生");
            System.out.println("3：修改学生");
            System.out.println("4：查询学生");
            System.out.println("5：退出学生");
            System.out.println("请输入你的选择");
            Scanner sc = new Scanner(System.in);
            String choose = sc.next();
            switch (choose) {
                case "1"-> addstudent(list);

                case "2"-> deletestudent(list);

                case "3"-> updatestudent(list);

                case "4"-> querystudent(list);

                case "5"->{
                    System.out.println("退出");
                    break loop;
                }
                default -> System.out.println("没有这个选项");
            }
        }

    }
    //添加学生
    public static void addstudent(ArrayList<Student> list){
        Student s=new Student();
        Scanner sc = new Scanner(System.in);
        String id;

       while (true) {
            System.out.println("请输入学生id");
            id = sc.next();
            boolean flag=loop(list,id);
            if (flag){
                System.out.println("id已经存在请重新录入");
            }else {
                break ;
            }
        }
        s.setId(id);
        System.out.println("请输入学生姓名");
        String name = sc.next();
        s.setName(name);
        System.out.println("请输入学生年龄");
        String age = sc.next();
        s.setAge(Integer.parseInt(age));
        System.out.println("请输入学生家庭地址");
        String address = sc.next();
        s.setAddress(address);
        list.add(s);
    }
    //删除学生
    public static void deletestudent(ArrayList<Student> list){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要删除学生的id");
        String id = sc.next();
        int index=getIndex(list,id);
        if (index>=0){
            list.remove(index);
            System.out.println("id为："+id+"的学生删除成功");
        }else{
            System.out.println("id不存在删除失败");
        }

    }
    //修改学生
    public static void updatestudent(ArrayList<Student> list){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要修改学生的id");
        String id = sc.next();
        int index=getIndex(list,id);
        if (index==-1){
            System.out.println("要修改的id"+id+"不存在，请重新输入");
            return;
        }
        Student s=list.get(index);
        System.out.println("请输入要修改的学生姓名");
        String newname = sc.next();
        s.setName(newname);
        System.out.println("请输入要修改的学生年龄");
        String newage = sc.next();
        s.setAge(Integer.parseInt(newage));
        System.out.println("请输入要修改的学生家庭住址");
        String newaddrees = sc.next();
        s.setAddress(newaddrees);
        System.out.println("修改成功");
    }
    //查询
    public static void querystudent(ArrayList<Student> list){
        if(list.size()==0){
            System.out.println("当前无学生信息");
        }
        System.out.println("id\t\t\t姓名\t年龄\t家庭住址");
        for(int i=0;i<list.size();i++){
            Student student = list.get(i);
            System.out.println(student.getId()+"\t\t"+student.getName()+"\t"+student.getAge()+"\t"+student.getAddress());
        }
//
    }
    //判断id在集合中是否存在
    public static boolean loop(ArrayList<Student> list ,String id){
//        for (Student student : list) {
//            String sid = student.getId();
//            if (sid.equals(id)) {
//                return true;
//            }
//        }
        return getIndex(list,id)>=0;
    }
    //通过id获取索引的方法
    public static int getIndex(ArrayList<Student> list ,String id){
        for (int i = 0; i < list.size(); i++) {
            Student student = list.get(i);
            String sid = student.getId();
            if(sid.equals(id)){
                return i;
            }
            
        }
        return -1;
    }
}