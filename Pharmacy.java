import java.util.*;
import java.sql.*;
import java.io.*;
import java.util.Iterator;

class Node {
    Medicines data;
    Node next;
    Node prev;

    Node(Medicines data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}



class DoublyLinkedList implements Iterable<Node> {
    Node head;
    Node tail;

    DoublyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    void addNode(Medicines data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    void display() {
        for (Node node : this) {
            System.out.println(node.data.getMedicine_name() + " " + node.data.getPrice() + " " + node.data.getQuantity());
        }
    }

    void removeNode(Node data) {
        Node current = head;

        while (current != null) {
            if (current.data.equals(data)) { 
                
                if (current == head) {
                    head = head.next;
                    if (head != null) {
                        head.prev = null;
                    } else {
                        tail = null; 
                    }
                }
               
                else if (current == tail) {
                    tail = tail.prev;
                    if (tail != null) {
                        tail.next = null;
                    }
                }
                
                else {
                    current.prev.next = current.next;
                    if (current.next != null) {
                        current.next.prev = current.prev;
                    }
                }
                return; 
            }
            current = current.next;
        }
    }

    
    
    public Iterator<Node> iterator() {
        return new DoublyLinkedListIterator();
    }

    
    private class DoublyLinkedListIterator implements Iterator<Node> {
        private Node current = head;

        
        public boolean hasNext() {
            return current != null;
        }

        
        public Node next() {
            Node temp = current;
            current = current.next;
            return temp;
        }
    }
}





class Medicines {
    String medicine_name;
    double price;
    int quantity;
    String m_date;
    String e_date;
    String description;

    Medicines(String medicine_name, double price, int quantity, String m_date, String e_date, String description) {
        this.medicine_name = medicine_name;
        this.price = price;
        this.quantity = quantity; 
        this.m_date = m_date;
        this.e_date = e_date;
        this.description = description;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getM_date() {
        return m_date;
    }

    public String getE_date() {
        return e_date;
    }

    public String getDescription() {
        return description;
    }
}

class Purchase {
    private String medicine_name;
    int p_quantity;

    Purchase(String medicine_name, int p_quantity) {
        this.medicine_name = medicine_name;
        this.p_quantity = p_quantity;
    }

    public String getMedicineName() {
        return medicine_name;
    }

    public int getQuantity() {
        return p_quantity;
    }

    Purchase() {
    }
}

class Main {
    static Connection con = null;
    static Scanner sc = new Scanner(System.in);
    static Statement st = null;
    static String sql = null;
    static PreparedStatement pst = null;
    static ResultSet rs = null;
    static DoublyLinkedList medi = new DoublyLinkedList();
    static LinkedList<Purchase> pur = new LinkedList<>();
    static double amount = 0;

    public static void main(String[] args) throws Exception {

        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy2", "root", "");
        System.out.println((con!=null)?"connection success":"connection failed");
        st = con.createStatement();
        
        sql = "select * from medicines";
        rs = st.executeQuery(sql);
        while (rs.next()) {
            Medicines m = new Medicines(rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5),
                    rs.getString(6), rs.getString(7));
            medi.addNode(m);
        }

        System.out.println("========>> Welcome to Ganesh Medical Store <<========");
        int choice, r;
        System.out.println("\nCould you please confirm who are you?");
        System.out.println("Press [1] if you are Customer.");
        System.out.println("Press [2] if you are Admin.");
        System.out.print("Enter your choice => ");
        choice = sc.nextInt();
        switch (choice) {
            case 1:
                boolean f1 = true;
                while (f1) {
                    System.out.println("\n\nPress [1] for Registration.");
                    System.out.println("Press [2] for Login.");
                    System.out.println("Press [3] for Exit.");
                    System.out.print("Enter your choice => ");
                    int ch1 = sc.nextInt();
                    switch (ch1) {
                        case 1:
                            System.out.print("\nEnter first name: ");
                            String c_fname = sc.next();
                            System.out.print("Enter last name: ");
                            String c_lname = sc.next();
                            System.out.print("Enter Gender: ");
                            String c_gender = sc.next();
                            System.out.print("Enter birth date(YYYY/MM/DD): ");
                            String c_birthdate = sc.next();
                            System.out.print("Enter email: ");
                            String c_email = sc.next();
                            System.out.print("Enter address: ");
                            String c_address = sc.next();
                            String customer_pass = "";
                            String c_phone_no = "";
                            String cust_id = "";
                            int t2 = 3;
                            int cnt0 = 0;
                            int cnt1 = 0;
                            int cnt2 = 0;
                            while (t2 >= 1) {
                                System.out.print("Enter Mobile no. : ");
                                sc.nextLine();
                                c_phone_no = sc.nextLine();
                                if (c_phone_no.length() == 10) {
                                    cnt1++;
                                    for (int i = 0; i < 10; i++) {
                                        if ((c_phone_no.charAt(i) >= 48) && (c_phone_no.charAt(i) <= 57)) {
                                            cnt1++;
                                        } else {
                                            System.out.print("");
                                        }
                                    }
                                    if (cnt1 == 11) {
                                        cnt0 = 1;
                                        break;
                                    } else {
                                        System.out.println("\nInvalid Mobile No.!");
                                        t2--;
                                    }
                                } else {
                                    System.out.println("\nInvalid number!");
                                    System.out.println("Please enter 10 digit number!");
                                    t2--;
                                }
                            }
                            if (cnt0 == 1) {
                                Random ran = new Random();
                                int iy = ran.nextInt(9);
                                int iz = ran.nextInt(9);
                                cust_id = c_fname + "" + iy + "" + iz;
                                System.out.println(
                                        "\nGenerated your Customer-Id : " + cust_id);
                                int t3 = 2;
                                while (t3 >= 1) {
                                    int cnt3 = 0;
                                    System.out.print("\nCreate Password : ");
                                    customer_pass = sc.nextLine();
                                    int lower_case = 0;
                                    int upper_case = 0;
                                    int digit_case = 0;
                                    for (int i = 0; i < customer_pass.length(); i++) {
                                        for (char j = 65; j <= 90; j++) {
                                            if (customer_pass.charAt(i) == j) {
                                                upper_case = 1;
                                                break;
                                            }
                                        }
                                    }
                                    if (upper_case == 1) {
                                        cnt3++;
                                    }
                                    for (int i = 0; i < customer_pass.length(); i++) {
                                        for (char j = 97; j <= 122; j++) {
                                            if (customer_pass.charAt(i) == j) {
                                                lower_case = 1;
                                                break;
                                            }
                                        }
                                    }
                                    if (lower_case == 1) {
                                        cnt3++;
                                    }
                                    for (int i = 0; i < customer_pass.length(); i++) {
                                        for (char j = 48; j <= 57; j++) {
                                            if (customer_pass.charAt(i) == j) {
                                                digit_case = 1;
                                                break;
                                            }
                                        }
                                    }
                                    if (digit_case == 1) {
                                        cnt3++;
                                    }
                                    if ((customer_pass.length() >= 8) && (customer_pass.length() <= 15)) {
                                        cnt3++;
                                    }
                                    System.out.print("Enter Confirm Password : ");
                                    String con_pass = sc.nextLine();
                                    if ((con_pass.equals(customer_pass)) && (cnt3 == 4)) {
                                        cnt2++;
                                        System.out.println("\nYour password must be created.");
                                        break;
                                    } else {
                                        System.out.println("\nIncorrect Password!");
                                        System.out.println("Please try again\n");
                                    }
                                }
                            } else {
                                System.out.print("");
                            }

                            if (cnt2 == 1) {
                                sql = "insert into customer values('" + cust_id + "','" + c_fname + "','" + c_lname
                                        + "','" + c_gender + "','" + c_birthdate + "'," + c_phone_no + ",'" + c_email
                                        + "','" + customer_pass + "','" + c_address + "')";
                                r = st.executeUpdate(sql);
                                if (r > 0) {
                                    System.out.println("\nCustomer registration successfully.");
                                    customerOperations(con);
                                } else {
                                    System.out.println("Customer registration fail.");
                                }
                            } else {
                                System.out.println("");
                            }
                            f1 = false;
                            break;

                        case 2:
                            int t4 = 3;
                            do {
                                System.out.print("\nEnter Customer id: ");
                                String check_cust_id = sc.next();
                                System.out.print("Enter password: ");
                                String check_cust_pass = sc.next();
                                sql = "select * from customer where cust_id=? AND customer_pass=?";
                                pst = con.prepareStatement(sql);
                                pst.setString(1, check_cust_id);
                                pst.setString(2, check_cust_pass);
                                rs = pst.executeQuery();
                                if (rs.next()) {
                                    System.out.println("\nLogin successfully.");
                                    break;
                                } else {
                                    System.out.println("\nInvalid customer ID or password. Login failed.");
                                    t4--;
                                }
                            } while (t4 != 0);
                            if (t4 == 0) {
                                System.out.println("\nYour account lock for 1 hour.");
                                System.exit(0);
                            } else {
                                customerOperations(con);
                            }
                            f1 = false;
                            break;
                        case 3:
                            f1 = false;
                            break;
                        default:
                            System.out.println("Enter valid choice");
                            break;
                    }
                }
                break;

            case 2:
                boolean f2 = true;
                while (f2) {
                    System.out.println("\n\nPress [1] for Login.");
                    System.out.println("Press [2] for Exit.");
                   
                    System.out.print("Enter your choice => ");
                    int ch1 = sc.nextInt();
                    switch (ch1) {
                       

                        case 1:
                            int t7 = 3;
                            do {
                                System.out.print("\nEnter Admin id: ");
                                String check_admin_id = sc.next();
                                System.out.print("Enter password: ");
                                String check_admin_pass = sc.next();
                                sql = "select * from admin where admin_id=? AND admin_pass=?";
                                pst = con.prepareStatement(sql);
                                pst.setString(1, check_admin_id);
                                pst.setString(2, check_admin_pass);
                                rs = pst.executeQuery();
                                if (rs.next()) {
                                    System.out.println("\nLogin successfully.");
                                    break;
                                } else {
                                    System.out.println("\nInvalid Admin ID or password. Login failed.");
                                    t7--;
                                }
                            } while (t7 != 0);
                            if (t7 == 0) {
                                System.out.println("\nYour account lock for 1 hour.");
                            } else {
                                adminOperations(con);
                            }
                            f2 = false;
                            break;

                        case 2:
                            f2 = false;
                            break;
                        default:
                            System.out.println("Enter valid choice");
                            break;
                    }
                }
                break;
            default:
                System.out.println("Enter valid choice.");
                break;
        }
        sc.close();
    }

    static void customerOperations(Connection con) throws Exception {
        int customerChoice;
        boolean z = true;
        do {
            System.out.println("\nCustomer Operations:");
            System.out.println("Press [1] for Add to cart Medicine.");
            System.out.println("Press [2] for Show Medicine.");
            System.out.println("Press [3] for Remove Medicine.");
            System.out.println("Press [4] for Request Medicine.");
            System.out.println("Press [5] Make a Payment.");
            System.out.println("Press [6] Exit.");
            System.out.print("Enter your choice: ");
            customerChoice = sc.nextInt();

            switch (customerChoice) {
                case 1:
                    amount += buyMedicine(con);
                    break;
                case 2:
                    showMedicine();
                    break;
                case 3:
                    amount -= removeMedicine(con);
                    break;
                case 4:
                    requestMedicine(con);
                    break;
                case 5:
                    bill(amount);
                    z = false;
                    break;
                case 6:
                    z = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (z);
    }

    static void adminOperations(Connection con) throws Exception {
        int adminChoice;
        do {
            System.out.println("\nAdmin Operations:");
            System.out.println("Press [1] for  Check Stock of Medicine.");
            System.out.println("Press [2] for Add New Medicine.");
            System.out.println("Press [3] for Remove Medicine.");
            System.out.println("Press [4] for Update the quantity of Medicines.");
            System.out.println("Press [5] for Check Customer Requests.");
            System.out.println("Press [6] for Exit.");
            System.out.print("Enter your choice: ");
            adminChoice = sc.nextInt();

            switch (adminChoice) {
                case 1:
                    stockCheckMedicine(con);
                    break;
                case 2:
                    addNewMedicine(con);
                    break;
                case 3:
                    removeOldMedicine(con);
                    break;
                case 4:
                    updateQuantity(con);
                    break;
                case 5:
                    checkCustomerRequests(con);
                    break;
                case 6:
                    System.out.println("Exiting admin operations.");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (adminChoice != 6);
    }

    static double getPrice(String name) {
        for (Node m : medi) {
            if (m.data.getMedicine_name().equalsIgnoreCase(name)) {
                return m.data.getPrice();
            }
        }
        return 0;
    }

    static int getQuan(String name) {
        for (Node medic : medi) {
            if (medic.data.getMedicine_name().equalsIgnoreCase(name)) {
                return medic.data.getQuantity();
            }
        }
        return 0;
    }

    static double buyMedicine(Connection con) throws Exception {
        System.out.print("\nEnter medicine name: ");
        String m_name = sc.next();

        System.out.print("Enter quantity to buy: ");
        int buy_quantity = sc.nextInt();

        Node m1 = null;
        for (Node medic : medi) {
            if (medic.data.getMedicine_name().equalsIgnoreCase(m_name)) {
                m1 = medic;
                break;
            }
        }
        if (m1 == null) {
            System.out.println("\nMedicine not found.");
            return 0;
        } else {
            int available = m1.data.getQuantity();

            if (buy_quantity > available) {
                System.out.println("\nNot enough quantity available for purchase.");
                return 0;
            } else {
                m1.data.quantity -= buy_quantity;
                double totalMedicinePrice = m1.data.getPrice() * buy_quantity;

                sql = "update medicines set m_quantity = ? where m_name = ?";
                pst = con.prepareStatement(sql);
                pst.setInt(1, available - buy_quantity);
                pst.setString(2, m_name);
                pst.executeUpdate();

                System.out.println("\nMedicine(s) bought successfully.");
                pur.add(new Purchase(m_name, buy_quantity));
                return totalMedicinePrice;
            }
        }
    }

    static void showMedicine() {
        System.out.println("\n===== Available Medicines =====");
        for (Node medicine : medi) {
            System.out.println("\nMedicine Name: " + medicine.data.getMedicine_name());
            System.out.println("Price: " + medicine.data.getPrice());
            System.out.println("Quantity: " + medicine.data.getQuantity());
            System.out.println("Manufacturing Date: " + medicine.data.getM_date());
            System.out.println("Expiry Date: " + medicine.data.getE_date());
            System.out.println("Description: " + medicine.data.getDescription());
            System.out.println("-----------------------");
        }
    }

    static double removeMedicine(Connection con) throws Exception {
        System.out.print("\nEnter the name of the medicine to remove: ");
        String medicine_remove = sc.next();
        System.out.print("Enter the quantity of the medicine to remove: ");
        int quan = sc.nextInt();

        boolean found = false;
        double medicineCost = 0;
        for (Purchase p1 : pur) {
            if (p1.getMedicineName().equalsIgnoreCase(medicine_remove)) {
                if (quan == p1.getQuantity()) {
                    medicineCost = quan * getPrice(medicine_remove);
                    pur.remove(p1);
                    sql = "update medicines set m_quantity = ? where m_name = ?";
                    pst = con.prepareStatement(sql);
                    pst.setInt(1, getQuan(medicine_remove) + quan);
                    pst.setString(2, medicine_remove);
                    pst.executeUpdate();
                    System.out.println("Medicine removed successfully.");
                    found = true;
                    break;
                } else if (quan < p1.getQuantity()) {
                    p1.p_quantity -= quan;
                    medicineCost = quan * getPrice(medicine_remove);
                    sql = "update medicines set m_quantity = ? where m_name = ?";
                    pst = con.prepareStatement(sql);
                    pst.setInt(1, getQuan(medicine_remove) + quan);
                    pst.setString(2, medicine_remove);
                    pst.executeUpdate();
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("Medicine not found.");
            return 0;
        }
        return medicineCost;
    }

    static void requestMedicine(Connection con) throws Exception {
        System.out.print("Enter your customer ID: ");
        String c_id = sc.next();
        System.out.print("Enter medicine name you want to request: ");
        String r_medicine_name = sc.next();
        System.out.print("Enter the quantity you want to request: ");
        int r_quantity = sc.nextInt();
    
        String sql = "{CALL insertrequest(?, ?, ?)}";
        try (CallableStatement cst = con.prepareCall(sql)) {
            cst.setString(1, c_id);
            cst.setString(2, r_medicine_name);
            cst.setInt(3, r_quantity);
    
            boolean hadResults = cst.execute();
    
          
            if (hadResults) {
                try {
                    ResultSet rs = cst.getResultSet();
                    while (rs.next()) {
                        String message = rs.getString("message");
                        System.out.println(message);
                    }
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } 
    }
    

    static void bill(double a) {
        String fileName = "MedicineBill.txt";
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write("****************Ganesh Medical Store******************");
            bw.write("\n-------------: Medicine Bill :-------------\n");
            for (Purchase p : pur) {
                double price = getPrice(p.getMedicineName());
                double total = price * p.getQuantity();
                bw.write("medicine name: " + p.getMedicineName() + "\n");
                bw.write("price: " + price + "\n");
                bw.write("Quantity: " + p.getQuantity() + "\n");
                bw.write("Total: " + total + "\n");
            }
            bw.write("\n-------------------------------------------\n");
            bw.write("\nTotal amount: " + a + "\n");
            double totalWithGST = a + (a * 0.04);
            bw.write("Total amount with GST(4%): " + totalWithGST + "\n");
            bw.write("Thank you for shopping! \n");
            bw.close();
            System.out.println("Bill has been successfully written to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while writing the bill: " + e.getMessage());
        }
    }

    static void stockCheckMedicine(Connection con) throws Exception {
        st = con.createStatement();
        sql = "select * from medicines";
        rs = st.executeQuery(sql);
        System.out.println("=======================================");
        while (rs.next()) {
            String medicineName = rs.getString("m_name");
            int quantity = rs.getInt("m_quantity");
            System.out.println("\nMedicine: " + medicineName + ", Quantity: " + quantity);
        }
        System.out.println("\n=======================================");
    }

    static void addNewMedicine(Connection con) throws Exception {
        st = con.createStatement();
        System.out.print("\nEnter medicine name: ");
        String m_name = sc.next();
        System.out.print("Enter medicine price: ");
        double m_price = sc.nextDouble();
        System.out.print("Enter medicine Quantity: ");
        int m_quantity = sc.nextInt();
        System.out.print("Enter manufacturing date: ");
        String m_date = sc.next();
        System.out.println("Enter expaired date: ");
        String e_date = sc.next();
        System.out.println("Enter descripition: ");
        String descr = sc.next();
        Medicines m = new Medicines(m_name, m_price, m_quantity, m_date, e_date, descr);
        medi.addNode(m);

        sql = "insert into medicines(m_name,m_price,m_quantity,m_mdate,m_edate,m_descr) values('" + m_name + "',"
                + m_price + "," + m_quantity + ",'" + m_date + "','" + e_date + "','" + descr + "')";
        int r = st.executeUpdate(sql);
        if (r > 0) {
            System.out.println("Add medicine successfully");
        } else {
            System.out.println("failed");
        }

    }

    static void removeOldMedicine(Connection con) throws Exception {
        System.out.print("\nEnter the name of the medicine to remove: ");
        String remove_medicine_name = sc.next();

        Node m1 = null;
        for (Node medicine : medi) {
            if (medicine.data.getMedicine_name().equalsIgnoreCase(remove_medicine_name)) {
                m1 = medicine;
                break;
            }
        }
        if (m1 != null) {
            medi.removeNode(m1);
            sql = "delete from medicines where m_name = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, remove_medicine_name);
            int r = pst.executeUpdate();
            if (r > 0) {
                System.out.println("Medicine removed.");
            }
        } else {
            System.out.println("Medicine not found.");
        }
    }

    static void updateQuantity(Connection con) throws Exception {
        System.out.print("\nEnter the name of the medicine to update quantity: ");
        String medi_name = sc.next();
        System.out.print("Enter the quantity of the medicine to update: ");
        int up_quan = sc.nextInt();
    
        String sql = "{CALL UpdateMedicineQuantity(?, ?)}";
        try (CallableStatement cst = con.prepareCall(sql)) {
            cst.setString(1, medi_name);
            cst.setInt(2, up_quan);
    
            boolean hadResults = cst.execute();
    
           
            if (hadResults) {
                try {
                    ResultSet rs = cst.getResultSet();
                    while (rs.next()) {
                        String message = rs.getString("message");
                        System.out.println("\n" + message);
                    }
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } 
    }
    

    static void checkCustomerRequests(Connection con) throws Exception {
        st = con.createStatement();
        sql = "select * from request_list";
        rs = st.executeQuery(sql);

        System.out.println("\n======= Customer Requests =======");
        while (rs.next()) {
            String requestId = rs.getString(1);
            String customerId = rs.getString(2);
            String medicineName = rs.getString(3);
            int quantity = rs.getInt(4);
            String status = rs.getString(5);

            System.out.println("Request ID: " + requestId);
            System.out.println("Customer ID: " + customerId);
            System.out.println("Medicine Name: " + medicineName);
            System.out.println("Quantity: " + quantity);
            System.out.println("Status: " + status);
            System.out.println("------------------------------");
        }
        System.out.println("=============================");

        System.out.print("\nDo you want to add a new medicine? (yes/no): ");
        String ch = sc.next();

        if (ch.equalsIgnoreCase("yes")) {
            addNewMedicine(con);
            System.out.print("\nEnter the request ID to mark as Completed: ");
            int complete = sc.nextInt();
            sql = "update request_list set status = 'Completed' where req_id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, complete);
            int r = pst.executeUpdate();
            if (r > 0) {
                System.out.println("\nRequest marked as Completed.");
            } else {
                System.out.println("Failed to update the request status.");
            }
        }
    }
}