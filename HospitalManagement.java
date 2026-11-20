import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class HospitalManagement extends JFrame {

    // =========================================================
    // DATABASE DETAILS
    // =========================================================

    private static final String URL =
            "jdbc:mysql://localhost:3306/hospital";

    private static final String USER = "root";

    private static final String PASSWORD = "root";


    // =========================================================
    // GUI COMPONENTS
    // =========================================================

    JTabbedPane tabbedPane;

    // ---------------------------------------------------------
    // BED AVAILABILITY COMPONENTS
    // ---------------------------------------------------------

    JTextField txtBedId;
    JTextField txtWardName;
    JTextField txtBedType;

    JComboBox<String> cmbBedStatus;

    JButton btnBedInsert;
    JButton btnBedUpdate;
    JButton btnBedDelete;
    JButton btnBedClear;
    JButton btnRecommendBed;
    JComboBox<String> cmbRequiredWard;
    JComboBox<String> cmbRequiredBedType;
    JComboBox<String> cmbPriority;
    JLabel lblRecommendation;

    JButton btnBedFirst;
    JButton btnBedPrevious;
    JButton btnBedNext;
    JButton btnBedLast;

    JTable bedTable;
    DefaultTableModel bedModel;


    // ---------------------------------------------------------
    // PATIENT COMPONENTS
    // ---------------------------------------------------------

    JTextField txtPatientId;
    JTextField txtPatientName;
    JTextField txtPatientAge;
    JTextField txtPatientGender;
    JTextField txtPatientDisease;
    JTextField txtPatientPhone;

    JButton btnPatientInsert;
    JButton btnPatientUpdate;
    JButton btnPatientDelete;
    JButton btnPatientClear;

    JButton btnPatientFirst;
    JButton btnPatientPrevious;
    JButton btnPatientNext;
    JButton btnPatientLast;

    JTable patientTable;
    DefaultTableModel patientModel;


    // ---------------------------------------------------------
    // STAFF COMPONENTS
    // ---------------------------------------------------------

    JTextField txtStaffId;
    JTextField txtStaffName;
    JTextField txtStaffRole;
    JTextField txtStaffDepartment;
    JTextField txtStaffPhone;

    JButton btnStaffInsert;
    JButton btnStaffUpdate;
    JButton btnStaffDelete;
    JButton btnStaffClear;

    JButton btnStaffFirst;
    JButton btnStaffPrevious;
    JButton btnStaffNext;
    JButton btnStaffLast;

    JTable staffTable;
    DefaultTableModel staffModel;


    // =========================================================
    // DATABASE CONNECTION
    // =========================================================

    Connection con;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    HospitalManagement() {

        setTitle("Hospital Bed Availability Management System");

        setSize(1000, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        // Connect to database
        connect();

        // Create GUI
        createGUI();

        setVisible(true);
    }


    // =========================================================
    // DATABASE CONNECTION
    // =========================================================

    private void connect() {

        try {

            Class.forName(
                    "com.mysql.cj.jdbc.Driver"
            );

            con = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println(
                    "Database Connected Successfully"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Connection Failed!\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // CREATE MAIN GUI
    // =========================================================

    private void createGUI() {

        setLayout(new BorderLayout());

        // -----------------------------------------------------
        // TITLE
        // -----------------------------------------------------

        JLabel title = new JLabel(
                "HOSPITAL MANAGEMENT SYSTEM",
                JLabel.CENTER
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 10, 15, 10
                )
        );

        add(
                title,
                BorderLayout.NORTH
        );


        // -----------------------------------------------------
        // TABS
        // -----------------------------------------------------

        tabbedPane = new JTabbedPane();

        createBedPanel();

        createPatientPanel();

        createStaffPanel();

        add(
                tabbedPane,
                BorderLayout.CENTER
        );
    }


    // =========================================================
    // BED AVAILABILITY PANEL
    // =========================================================

    private void createBedPanel() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));


        // -----------------------------------------------------
        // FORM PANEL
        // -----------------------------------------------------

        JPanel formPanel =
                new JPanel(new GridBagLayout());

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(8, 8, 8, 8);

        gbc.anchor =
                GridBagConstraints.WEST;


        // Bed ID

        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(
                new JLabel("Bed ID:"),
                gbc
        );

        txtBedId =
                new JTextField(15);

        gbc.gridx = 1;

        formPanel.add(
                txtBedId,
                gbc
        );


        // Ward Name

        gbc.gridx = 0;
        gbc.gridy = 1;

        formPanel.add(
                new JLabel("Ward Name:"),
                gbc
        );

        txtWardName =
                new JTextField(15);

        gbc.gridx = 1;

        formPanel.add(
                txtWardName,
                gbc
        );


        // Bed Type

        gbc.gridx = 0;
        gbc.gridy = 2;

        formPanel.add(
                new JLabel("Bed Type:"),
                gbc
        );

        txtBedType =
                new JTextField(15);

        gbc.gridx = 1;

        formPanel.add(
                txtBedType,
                gbc
        );


        // Bed Status

        gbc.gridx = 0;
        gbc.gridy = 3;

        formPanel.add(
                new JLabel("Bed Status:"),
                gbc
        );

        cmbBedStatus =
                new JComboBox<>(
                        new String[]{
                                "Available",
                                "Occupied",
                                "Maintenance"
                        }
                );

        gbc.gridx = 1;

        formPanel.add(
                cmbBedStatus,
                gbc
        );


        // -----------------------------------------------------
        // BUTTON PANEL
        // -----------------------------------------------------

        JPanel buttonPanel =
                new JPanel(new FlowLayout());

        btnBedInsert =
                new JButton("Insert");

        btnBedUpdate =
                new JButton("Update");

        btnBedDelete =
                new JButton("Delete");

        btnBedClear =
                new JButton("Clear");

        buttonPanel.add(btnBedInsert);
        buttonPanel.add(btnBedUpdate);
        buttonPanel.add(btnBedDelete);
        buttonPanel.add(btnBedClear);

        // -----------------------------------------------------
        // SMART BED RECOMMENDATION
        // -----------------------------------------------------

        JPanel recommendationPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        recommendationPanel.add(new JLabel("Required Ward:"));
        cmbRequiredWard = new JComboBox<>(
                new String[]{"Any", "General Ward", "ICU", "Emergency", "Private Ward"});
        recommendationPanel.add(cmbRequiredWard);

        recommendationPanel.add(new JLabel("Bed Type:"));
        cmbRequiredBedType = new JComboBox<>(
                new String[]{"Any", "Normal", "ICU Bed", "Emergency", "Private"});
        recommendationPanel.add(cmbRequiredBedType);

        recommendationPanel.add(new JLabel("Priority:"));
        cmbPriority = new JComboBox<>(
                new String[]{"Normal", "Emergency"});
        recommendationPanel.add(cmbPriority);

        btnRecommendBed = new JButton("Recommend Bed");
        recommendationPanel.add(btnRecommendBed);

        lblRecommendation = new JLabel(" ");
        recommendationPanel.add(lblRecommendation);

        btnRecommendBed.addActionListener(
                e -> recommendBed()
        );


        // -----------------------------------------------------
        // TOP PANEL
        // -----------------------------------------------------

        JPanel topPanel =
                new JPanel(new BorderLayout());

        topPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        topPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.add(topPanel, BorderLayout.NORTH);
        upperPanel.add(recommendationPanel, BorderLayout.SOUTH);

        mainPanel.add(
                upperPanel,
                BorderLayout.NORTH
        );


        // -----------------------------------------------------
        // TABLE
        // -----------------------------------------------------

        bedModel =
                new DefaultTableModel();

        bedModel.addColumn("Bed ID");
        bedModel.addColumn("Ward Name");
        bedModel.addColumn("Bed Type");
        bedModel.addColumn("Bed Status");

        bedTable =
                new JTable(bedModel);

        bedTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(bedTable);

        mainPanel.add(
                createSearchPanel(bedTable, bedModel, scrollPane, "Search bed records..."),
                BorderLayout.CENTER
        );


        // -----------------------------------------------------
        // NAVIGATION
        // -----------------------------------------------------

        JPanel navigationPanel =
                new JPanel(new FlowLayout());

        btnBedFirst =
                new JButton("First");

        btnBedPrevious =
                new JButton("Previous");

        btnBedNext =
                new JButton("Next");

        btnBedLast =
                new JButton("Last");

        navigationPanel.add(btnBedFirst);
        navigationPanel.add(btnBedPrevious);
        navigationPanel.add(btnBedNext);
        navigationPanel.add(btnBedLast);

        mainPanel.add(
                navigationPanel,
                BorderLayout.SOUTH
        );


        // -----------------------------------------------------
        // EVENTS
        // -----------------------------------------------------

        btnBedInsert.addActionListener(
                e -> insertBed()
        );

        btnBedUpdate.addActionListener(
                e -> updateBed()
        );

        btnBedDelete.addActionListener(
                e -> deleteBed()
        );

        btnBedClear.addActionListener(
                e -> clearBed()
        );

        btnBedFirst.addActionListener(
                e -> firstBed()
        );

        btnBedPrevious.addActionListener(
                e -> previousBed()
        );

        btnBedNext.addActionListener(
                e -> nextBed()
        );

        btnBedLast.addActionListener(
                e -> lastBed()
        );


        // -----------------------------------------------------
        // TABLE CLICK
        // -----------------------------------------------------

        bedTable.addMouseListener(
                new MouseAdapter() {

                    public void mouseClicked(
                            MouseEvent e) {

                        int row =
                                bedTable.getSelectedRow();

                        if (row >= 0) {

                            txtBedId.setText(
                                    bedModel.getValueAt(
                                            row, 0
                                    ).toString()
                            );

                            txtWardName.setText(
                                    bedModel.getValueAt(
                                            row, 1
                                    ).toString()
                            );

                            txtBedType.setText(
                                    bedModel.getValueAt(
                                            row, 2
                                    ).toString()
                            );

                            cmbBedStatus.setSelectedItem(
                                    bedModel.getValueAt(
                                            row, 3
                                    ).toString()
                            );
                        }
                    }
                }
        );


        // Load data
        loadBedData();


        tabbedPane.addTab(
                "Bed Availability",
                mainPanel
        );
    }


    // =========================================================
    // PATIENT PANEL
    // =========================================================

    private void createPatientPanel() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));


        // -----------------------------------------------------
        // FORM
        // -----------------------------------------------------

        JPanel formPanel =
                new JPanel(new GridBagLayout());

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(6, 8, 6, 8);

        gbc.anchor =
                GridBagConstraints.WEST;


        // Patient ID

        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(
                new JLabel("Patient ID:"),
                gbc
        );

        txtPatientId =
                new JTextField(15);

        gbc.gridx = 1;

        formPanel.add(
                txtPatientId,
                gbc
        );


        // Patient Name

        gbc.gridx = 0;
        gbc.gridy = 1;

        formPanel.add(
                new JLabel("Patient Name:"),
                gbc
        );

        txtPatientName =
                new JTextField(15);

        gbc.gridx = 1;

        formPanel.add(
                txtPatientName,
                gbc
        );


        // Age

        gbc.gridx = 0;
        gbc.gridy = 2;

        formPanel.add(
                new JLabel("Age:"),
                gbc
        );

        txtPatientAge =
                new JTextField(15);

        gbc.gridx = 1;

        formPanel.add(
                txtPatientAge,
                gbc
        );


        // Gender

        gbc.gridx = 0;
        gbc.gridy = 3;

        formPanel.add(
                new JLabel("Gender:"),
                gbc
        );

        txtPatientGender =
                new JTextField(15);

        gbc.gridx = 1;

        formPanel.add(
                txtPatientGender,
                gbc
        );


        // Disease

        gbc.gridx = 0;
        gbc.gridy = 4;

        formPanel.add(
                new JLabel("Disease:"),
                gbc
        );

        txtPatientDisease =
                new JTextField(15);

        gbc.gridx = 1;

        formPanel.add(
                txtPatientDisease,
                gbc
        );


        // Phone

        gbc.gridx = 0;
        gbc.gridy = 5;

        formPanel.add(
                new JLabel("Phone:"),
                gbc
        );

        txtPatientPhone =
                new JTextField(15);

        gbc.gridx = 1;

        formPanel.add(
                txtPatientPhone,
                gbc
        );


        // -----------------------------------------------------
        // BUTTONS
        // -----------------------------------------------------

        JPanel buttonPanel =
                new JPanel(new FlowLayout());

        btnPatientInsert =
                new JButton("Insert");

        btnPatientUpdate =
                new JButton("Update");

        btnPatientDelete =
                new JButton("Delete");

        btnPatientClear =
                new JButton("Clear");

        buttonPanel.add(btnPatientInsert);
        buttonPanel.add(btnPatientUpdate);
        buttonPanel.add(btnPatientDelete);
        buttonPanel.add(btnPatientClear);


        JPanel topPanel =
                new JPanel(new BorderLayout());

        topPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        topPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );


        // -----------------------------------------------------
        // TABLE
        // -----------------------------------------------------

        patientModel =
                new DefaultTableModel();

        patientModel.addColumn("Patient ID");
        patientModel.addColumn("Patient Name");
        patientModel.addColumn("Age");
        patientModel.addColumn("Gender");
        patientModel.addColumn("Disease");
        patientModel.addColumn("Phone");

        patientTable =
                new JTable(patientModel);

        patientTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(patientTable);

        mainPanel.add(
                createSearchPanel(patientTable, patientModel, scrollPane, "Search patient records..."),
                BorderLayout.CENTER
        );


        // -----------------------------------------------------
        // NAVIGATION
        // -----------------------------------------------------

        JPanel navigationPanel =
                new JPanel(new FlowLayout());

        btnPatientFirst =
                new JButton("First");

        btnPatientPrevious =
                new JButton("Previous");

        btnPatientNext =
                new JButton("Next");

        btnPatientLast =
                new JButton("Last");

        navigationPanel.add(btnPatientFirst);
        navigationPanel.add(btnPatientPrevious);
        navigationPanel.add(btnPatientNext);
        navigationPanel.add(btnPatientLast);

        mainPanel.add(
                navigationPanel,
                BorderLayout.SOUTH
        );


        // -----------------------------------------------------
        // EVENTS
        // -----------------------------------------------------

        btnPatientInsert.addActionListener(
                e -> insertPatient()
        );

        btnPatientUpdate.addActionListener(
                e -> updatePatient()
        );

        btnPatientDelete.addActionListener(
                e -> deletePatient()
        );

        btnPatientClear.addActionListener(
                e -> clearPatient()
        );

        btnPatientFirst.addActionListener(
                e -> firstPatient()
        );

        btnPatientPrevious.addActionListener(
                e -> previousPatient()
        );

        btnPatientNext.addActionListener(
                e -> nextPatient()
        );

        btnPatientLast.addActionListener(
                e -> lastPatient()
        );


        // -----------------------------------------------------
        // TABLE CLICK
        // -----------------------------------------------------

        patientTable.addMouseListener(
                new MouseAdapter() {

                    public void mouseClicked(
                            MouseEvent e) {

                        int row =
                                patientTable.getSelectedRow();

                        if (row >= 0) {

                            txtPatientId.setText(
                                    patientModel.getValueAt(
                                            row, 0
                                    ).toString()
                            );

                            txtPatientName.setText(
                                    patientModel.getValueAt(
                                            row, 1
                                    ).toString()
                            );

                            txtPatientAge.setText(
                                    patientModel.getValueAt(
                                            row, 2
                                    ).toString()
                            );

                            txtPatientGender.setText(
                                    patientModel.getValueAt(
                                            row, 3
                                    ).toString()
                            );

                            txtPatientDisease.setText(
                                    patientModel.getValueAt(
                                            row, 4
                                    ).toString()
                            );

                            txtPatientPhone.setText(
                                    patientModel.getValueAt(
                                            row, 5
                                    ).toString()
                            );
                        }
                    }
                }
        );


        // Load data
        loadPatientData();


        tabbedPane.addTab(
                "Patient Details",
                mainPanel
        );
    }


    // =========================================================
    // STAFF PANEL
    // =========================================================

    private void createStaffPanel() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));


        // -----------------------------------------------------
        // FORM
        // -----------------------------------------------------

        JPanel formPanel =
                new JPanel(new GridBagLayout());

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(8, 8, 8, 8);

        gbc.anchor =
                GridBagConstraints.WEST;


        // Staff ID

        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(
                new JLabel("Staff ID:"),
                gbc
        );

        txtStaffId =
                new JTextField(15);

        gbc.gridx = 1;

        formPanel.add(
                txtStaffId,
                gbc
        );


        // Staff Name

        gbc.gridx = 0;
        gbc.gridy = 1;

        formPanel.add(
                new JLabel("Staff Name:"),
                gbc
        );

        txtStaffName =
                new JTextField(15);

        gbc.gridx = 1;

        formPanel.add(
                txtStaffName,
                gbc
        );


        // Role

        gbc.gridx = 0;
        gbc.gridy = 2;

        formPanel.add(
                new JLabel("Role:"),
                gbc
        );

        txtStaffRole =
                new JTextField(15);

        gbc.gridx = 1;

        formPanel.add(
                txtStaffRole,
                gbc
        );


        // Department

        gbc.gridx = 0;
        gbc.gridy = 3;

        formPanel.add(
                new JLabel("Department:"),
                gbc
        );

        txtStaffDepartment =
                new JTextField(15);

        gbc.gridx = 1;

        formPanel.add(
                txtStaffDepartment,
                gbc
        );


        // Phone

        gbc.gridx = 0;
        gbc.gridy = 4;

        formPanel.add(
                new JLabel("Phone:"),
                gbc
        );

        txtStaffPhone =
                new JTextField(15);

        gbc.gridx = 1;

        formPanel.add(
                txtStaffPhone,
                gbc
        );


        // -----------------------------------------------------
        // BUTTONS
        // -----------------------------------------------------

        JPanel buttonPanel =
                new JPanel(new FlowLayout());

        btnStaffInsert =
                new JButton("Insert");

        btnStaffUpdate =
                new JButton("Update");

        btnStaffDelete =
                new JButton("Delete");

        btnStaffClear =
                new JButton("Clear");

        buttonPanel.add(btnStaffInsert);
        buttonPanel.add(btnStaffUpdate);
        buttonPanel.add(btnStaffDelete);
        buttonPanel.add(btnStaffClear);


        JPanel topPanel =
                new JPanel(new BorderLayout());

        topPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        topPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );


        // -----------------------------------------------------
        // TABLE
        // -----------------------------------------------------

        staffModel =
                new DefaultTableModel();

        staffModel.addColumn("Staff ID");
        staffModel.addColumn("Staff Name");
        staffModel.addColumn("Role");
        staffModel.addColumn("Department");
        staffModel.addColumn("Phone");

        staffTable =
                new JTable(staffModel);

        staffTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(staffTable);

        mainPanel.add(
                createSearchPanel(staffTable, staffModel, scrollPane, "Search staff records..."),
                BorderLayout.CENTER
        );


        // -----------------------------------------------------
        // NAVIGATION
        // -----------------------------------------------------

        JPanel navigationPanel =
                new JPanel(new FlowLayout());

        btnStaffFirst =
                new JButton("First");

        btnStaffPrevious =
                new JButton("Previous");

        btnStaffNext =
                new JButton("Next");

        btnStaffLast =
                new JButton("Last");

        navigationPanel.add(btnStaffFirst);
        navigationPanel.add(btnStaffPrevious);
        navigationPanel.add(btnStaffNext);
        navigationPanel.add(btnStaffLast);

        mainPanel.add(
                navigationPanel,
                BorderLayout.SOUTH
        );


        // -----------------------------------------------------
        // EVENTS
        // -----------------------------------------------------

        btnStaffInsert.addActionListener(
                e -> insertStaff()
        );

        btnStaffUpdate.addActionListener(
                e -> updateStaff()
        );

        btnStaffDelete.addActionListener(
                e -> deleteStaff()
        );

        btnStaffClear.addActionListener(
                e -> clearStaff()
        );

        btnStaffFirst.addActionListener(
                e -> firstStaff()
        );

        btnStaffPrevious.addActionListener(
                e -> previousStaff()
        );

        btnStaffNext.addActionListener(
                e -> nextStaff()
        );

        btnStaffLast.addActionListener(
                e -> lastStaff()
        );


        // -----------------------------------------------------
        // TABLE CLICK
        // -----------------------------------------------------

        staffTable.addMouseListener(
                new MouseAdapter() {

                    public void mouseClicked(
                            MouseEvent e) {

                        int row =
                                staffTable.getSelectedRow();

                        if (row >= 0) {

                            txtStaffId.setText(
                                    staffModel.getValueAt(
                                            row, 0
                                    ).toString()
                            );

                            txtStaffName.setText(
                                    staffModel.getValueAt(
                                            row, 1
                                    ).toString()
                            );

                            txtStaffRole.setText(
                                    staffModel.getValueAt(
                                            row, 2
                                    ).toString()
                            );

                            txtStaffDepartment.setText(
                                    staffModel.getValueAt(
                                            row, 3
                                    ).toString()
                            );

                            txtStaffPhone.setText(
                                    staffModel.getValueAt(
                                            row, 4
                                    ).toString()
                            );
                        }
                    }
                }
        );


        // Load data
        loadStaffData();


        tabbedPane.addTab(
                "Staff Details",
                mainPanel
        );
    }


    // =========================================================
    // BED - LOAD DATA
    // =========================================================

    private void loadBedData() {

        try {

            bedModel.setRowCount(0);

            String sql =
                    "SELECT * FROM BedAvailability " +
                    "ORDER BY bed_id";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                bedModel.addRow(
                        new Object[]{
                                rs.getInt("bed_id"),
                                rs.getString("ward_name"),
                                rs.getString("bed_type"),
                                rs.getString("bed_status")
                        }
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error Loading Bed Records!\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // BED - INSERT
    // =========================================================

    private void insertBed() {

        if (!validateBed())
            return;

        try {

            String sql =
                    "INSERT INTO BedAvailability " +
                    "(bed_id, ward_name, bed_type, bed_status) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setInt(
                    1,
                    Integer.parseInt(
                            txtBedId.getText().trim()
                    )
            );

            pst.setString(
                    2,
                    txtWardName.getText().trim()
            );

            pst.setString(
                    3,
                    txtBedType.getText().trim()
            );

            pst.setString(
                    4,
                    cmbBedStatus.getSelectedItem()
                            .toString()
            );

            pst.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Bed Record Inserted Successfully!"
            );

            loadBedData();

            clearBed();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Insert Failed!\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // BED - UPDATE
    // =========================================================

    private void updateBed() {

        if (!validateBed())
            return;

        try {

            String sql =
                    "UPDATE BedAvailability SET " +
                    "ward_name = ?, " +
                    "bed_type = ?, " +
                    "bed_status = ? " +
                    "WHERE bed_id = ?";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(
                    1,
                    txtWardName.getText().trim()
            );

            pst.setString(
                    2,
                    txtBedType.getText().trim()
            );

            pst.setString(
                    3,
                    cmbBedStatus.getSelectedItem()
                            .toString()
            );

            pst.setInt(
                    4,
                    Integer.parseInt(
                            txtBedId.getText().trim()
                    )
            );

            int rows =
                    pst.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Bed Record Updated Successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Bed ID not found!"
                );
            }

            loadBedData();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Update Failed!\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // BED - DELETE
    // =========================================================

    private void deleteBed() {

        if (txtBedId.getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter Bed ID to delete!"
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete " +
                                "this bed?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION)
            return;

        try {

            String sql =
                    "DELETE FROM BedAvailability " +
                    "WHERE bed_id = ?";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setInt(
                    1,
                    Integer.parseInt(
                            txtBedId.getText().trim()
                    )
            );

            int rows =
                    pst.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Bed Record Deleted Successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Bed ID not found!"
                );
            }

            loadBedData();

            clearBed();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Delete Failed!\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // BED - NAVIGATION
    // =========================================================

    private void firstBed() {

        if (bedModel.getRowCount() == 0)
            return;

        showBed(0);
    }


    private void previousBed() {

        int row =
                bedTable.getSelectedRow();

        if (row > 0) {

            showBed(row - 1);

        } else if (row == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Already at the first record."
            );
        }
    }


    private void nextBed() {

        int row =
                bedTable.getSelectedRow();

        if (row >= 0 &&
                row < bedModel.getRowCount() - 1) {

            showBed(row + 1);

        } else if (
                row == bedModel.getRowCount() - 1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Already at the last record."
            );
        }
    }


    private void lastBed() {

        if (bedModel.getRowCount() == 0)
            return;

        showBed(
                bedModel.getRowCount() - 1
        );
    }


    private void showBed(int row) {

        bedTable.setRowSelectionInterval(
                row,
                row
        );

        txtBedId.setText(
                bedModel.getValueAt(
                        row, 0
                ).toString()
        );

        txtWardName.setText(
                bedModel.getValueAt(
                        row, 1
                ).toString()
        );

        txtBedType.setText(
                bedModel.getValueAt(
                        row, 2
                ).toString()
        );

        cmbBedStatus.setSelectedItem(
                bedModel.getValueAt(
                        row, 3
                ).toString()
        );
    }


    // =========================================================
    // SMART BED RECOMMENDATION
    // =========================================================

    private void recommendBed() {

        String requiredWard =
                cmbRequiredWard.getSelectedItem().toString();

        String requiredBedType =
                cmbRequiredBedType.getSelectedItem().toString();

        String priority =
                cmbPriority.getSelectedItem().toString();

        try {

            StringBuilder sql = new StringBuilder(
                    "SELECT bed_id, ward_name, bed_type, bed_status " +
                    "FROM BedAvailability " +
                    "WHERE bed_status = 'Available'"
            );

            if (!requiredWard.equals("Any")) {
                sql.append(" AND LOWER(ward_name) = LOWER(?)");
            }

            if (!requiredBedType.equals("Any")) {
                sql.append(" AND LOWER(bed_type) = LOWER(?)");
            }

            if (requiredWard.equals("Any") &&
                    priority.equals("Emergency")) {
                sql.append(
                        " ORDER BY " +
                        "CASE WHEN LOWER(ward_name) = 'emergency' THEN 0 ELSE 1 END, " +
                        "bed_id LIMIT 1"
                );
            } else {
                sql.append(" ORDER BY bed_id LIMIT 1");
            }

            PreparedStatement pst =
                    con.prepareStatement(sql.toString());

            int index = 1;

            if (!requiredWard.equals("Any")) {
                pst.setString(index++, requiredWard);
            }

            if (!requiredBedType.equals("Any")) {
                pst.setString(index++, requiredBedType);
            }

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                int bedId = rs.getInt("bed_id");
                String ward = rs.getString("ward_name");
                String bedType = rs.getString("bed_type");

                String priorityMessage =
                        priority.equals("Emergency")
                                ? "Emergency priority considered."
                                : "Normal priority.";

                String message =
                        "Recommended Bed: " + bedId +
                        "\nWard: " + ward +
                        "\nBed Type: " + bedType +
                        "\nStatus: Available" +
                        "\n" + priorityMessage;

                lblRecommendation.setText(
                        "Recommended Bed: " + bedId +
                        " (" + ward + ", " + bedType + ")"
                );

                JOptionPane.showMessageDialog(
                        this,
                        message,
                        "Smart Bed Recommendation",
                        JOptionPane.INFORMATION_MESSAGE
                );

                rs.close();
                pst.close();

            } else {

                lblRecommendation.setText(
                        "No suitable available bed found."
                );

                JOptionPane.showMessageDialog(
                        this,
                        "No suitable available bed was found for the selected requirements.",
                        "Bed Recommendation",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Bed Recommendation Failed!\n" +
                            e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // BED - CLEAR
    // =========================================================

    private void clearBed() {

        txtBedId.setText("");
        txtWardName.setText("");
        txtBedType.setText("");

        cmbBedStatus.setSelectedIndex(0);

        bedTable.clearSelection();

        txtBedId.requestFocus();
    }


    // =========================================================
    // BED - VALIDATION
    // =========================================================

    private boolean validateBed() {

        if (txtBedId.getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Bed ID."
            );

            txtBedId.requestFocus();

            return false;
        }

        try {

            Integer.parseInt(
                    txtBedId.getText().trim()
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Bed ID must be an integer."
            );

            txtBedId.requestFocus();

            return false;
        }


        if (txtWardName.getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Ward Name."
            );

            txtWardName.requestFocus();

            return false;
        }


        if (txtBedType.getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Bed Type."
            );

            txtBedType.requestFocus();

            return false;
        }

        return true;
    }


    // =========================================================
    // PATIENT - LOAD DATA
    // =========================================================

    private void loadPatientData() {

        try {

            patientModel.setRowCount(0);

            String sql =
                    "SELECT * FROM PatientDetails " +
                    "ORDER BY patient_id";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                patientModel.addRow(
                        new Object[]{
                                rs.getInt("patient_id"),
                                rs.getString("patient_name"),
                                rs.getInt("age"),
                                rs.getString("gender"),
                                rs.getString("disease"),
                                rs.getString("phone")
                        }
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error Loading Patient Records!\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // PATIENT - INSERT
    // =========================================================

    private void insertPatient() {

        if (!validatePatient())
            return;

        try {

            String sql =
                    "INSERT INTO PatientDetails " +
                    "(patient_id, patient_name, age, " +
                    "gender, disease, phone) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setInt(
                    1,
                    Integer.parseInt(
                            txtPatientId.getText().trim()
                    )
            );

            pst.setString(
                    2,
                    txtPatientName.getText().trim()
            );

            pst.setInt(
                    3,
                    Integer.parseInt(
                            txtPatientAge.getText().trim()
                    )
            );

            pst.setString(
                    4,
                    txtPatientGender.getText().trim()
            );

            pst.setString(
                    5,
                    txtPatientDisease.getText().trim()
            );

            pst.setString(
                    6,
                    txtPatientPhone.getText().trim()
            );

            pst.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Patient Record Inserted Successfully!"
            );

            loadPatientData();

            clearPatient();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Insert Failed!\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // PATIENT - UPDATE
    // =========================================================

    private void updatePatient() {

        if (!validatePatient())
            return;

        try {

            String sql =
                    "UPDATE PatientDetails SET " +
                    "patient_name = ?, " +
                    "age = ?, " +
                    "gender = ?, " +
                    "disease = ?, " +
                    "phone = ? " +
                    "WHERE patient_id = ?";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(
                    1,
                    txtPatientName.getText().trim()
            );

            pst.setInt(
                    2,
                    Integer.parseInt(
                            txtPatientAge.getText().trim()
                    )
            );

            pst.setString(
                    3,
                    txtPatientGender.getText().trim()
            );

            pst.setString(
                    4,
                    txtPatientDisease.getText().trim()
            );

            pst.setString(
                    5,
                    txtPatientPhone.getText().trim()
            );

            pst.setInt(
                    6,
                    Integer.parseInt(
                            txtPatientId.getText().trim()
                    )
            );

            int rows =
                    pst.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Patient Record Updated Successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Patient ID not found!"
                );
            }

            loadPatientData();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Update Failed!\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // PATIENT - DELETE
    // =========================================================

    private void deletePatient() {

        if (txtPatientId.getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter Patient ID to delete!"
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete " +
                                "this patient?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION)
            return;

        try {

            String sql =
                    "DELETE FROM PatientDetails " +
                    "WHERE patient_id = ?";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setInt(
                    1,
                    Integer.parseInt(
                            txtPatientId.getText().trim()
                    )
            );

            int rows =
                    pst.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Patient Record Deleted Successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Patient ID not found!"
                );
            }

            loadPatientData();

            clearPatient();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Delete Failed!\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // PATIENT - NAVIGATION
    // =========================================================

    private void firstPatient() {

        if (patientModel.getRowCount() == 0)
            return;

        showPatient(0);
    }


    private void previousPatient() {

        int row =
                patientTable.getSelectedRow();

        if (row > 0) {

            showPatient(row - 1);

        } else if (row == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Already at the first record."
            );
        }
    }


    private void nextPatient() {

        int row =
                patientTable.getSelectedRow();

        if (row >= 0 &&
                row < patientModel.getRowCount() - 1) {

            showPatient(row + 1);

        } else if (
                row == patientModel.getRowCount() - 1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Already at the last record."
            );
        }
    }


    private void lastPatient() {

        if (patientModel.getRowCount() == 0)
            return;

        showPatient(
                patientModel.getRowCount() - 1
        );
    }


    private void showPatient(int row) {

        patientTable.setRowSelectionInterval(
                row,
                row
        );

        txtPatientId.setText(
                patientModel.getValueAt(
                        row, 0
                ).toString()
        );

        txtPatientName.setText(
                patientModel.getValueAt(
                        row, 1
                ).toString()
        );

        txtPatientAge.setText(
                patientModel.getValueAt(
                        row, 2
                ).toString()
        );

        txtPatientGender.setText(
                patientModel.getValueAt(
                        row, 3
                ).toString()
        );

        txtPatientDisease.setText(
                patientModel.getValueAt(
                        row, 4
                ).toString()
        );

        txtPatientPhone.setText(
                patientModel.getValueAt(
                        row, 5
                ).toString()
        );
    }


    // =========================================================
    // PATIENT - CLEAR
    // =========================================================

    private void clearPatient() {

        txtPatientId.setText("");
        txtPatientName.setText("");
        txtPatientAge.setText("");
        txtPatientGender.setText("");
        txtPatientDisease.setText("");
        txtPatientPhone.setText("");

        patientTable.clearSelection();

        txtPatientId.requestFocus();
    }


    // =========================================================
    // PATIENT - VALIDATION
    // =========================================================

    private boolean validatePatient() {

        if (txtPatientId.getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Patient ID."
            );

            txtPatientId.requestFocus();

            return false;
        }

        try {

            Integer.parseInt(
                    txtPatientId.getText().trim()
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Patient ID must be an integer."
            );

            txtPatientId.requestFocus();

            return false;
        }


        if (txtPatientName.getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Patient Name."
            );

            txtPatientName.requestFocus();

            return false;
        }


        if (txtPatientAge.getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Age."
            );

            txtPatientAge.requestFocus();

            return false;
        }

        try {

            int age =
                    Integer.parseInt(
                            txtPatientAge.getText().trim()
                    );

            if (age <= 0 || age > 120) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid age."
                );

                txtPatientAge.requestFocus();

                return false;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age must be an integer."
            );

            txtPatientAge.requestFocus();

            return false;
        }


        if (txtPatientGender.getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Gender."
            );

            txtPatientGender.requestFocus();

            return false;
        }


        if (txtPatientDisease.getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Disease."
            );

            txtPatientDisease.requestFocus();

            return false;
        }

        return true;
    }


    // =========================================================
    // STAFF - LOAD DATA
    // =========================================================

    private void loadStaffData() {

        try {

            staffModel.setRowCount(0);

            String sql =
                    "SELECT * FROM Staff " +
                    "ORDER BY staff_id";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                staffModel.addRow(
                        new Object[]{
                                rs.getInt("staff_id"),
                                rs.getString("staff_name"),
                                rs.getString("role"),
                                rs.getString("department"),
                                rs.getString("phone")
                        }
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error Loading Staff Records!\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // STAFF - INSERT
    // =========================================================

    private void insertStaff() {

        if (!validateStaff())
            return;

        try {

            String sql =
                    "INSERT INTO Staff " +
                    "(staff_id, staff_name, role, " +
                    "department, phone) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setInt(
                    1,
                    Integer.parseInt(
                            txtStaffId.getText().trim()
                    )
            );

            pst.setString(
                    2,
                    txtStaffName.getText().trim()
            );

            pst.setString(
                    3,
                    txtStaffRole.getText().trim()
            );

            pst.setString(
                    4,
                    txtStaffDepartment.getText().trim()
            );

            pst.setString(
                    5,
                    txtStaffPhone.getText().trim()
            );

            pst.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Staff Record Inserted Successfully!"
            );

            loadStaffData();

            clearStaff();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Insert Failed!\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // STAFF - UPDATE
    // =========================================================

    private void updateStaff() {

        if (!validateStaff())
            return;

        try {

            String sql =
                    "UPDATE Staff SET " +
                    "staff_name = ?, " +
                    "role = ?, " +
                    "department = ?, " +
                    "phone = ? " +
                    "WHERE staff_id = ?";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(
                    1,
                    txtStaffName.getText().trim()
            );

            pst.setString(
                    2,
                    txtStaffRole.getText().trim()
            );

            pst.setString(
                    3,
                    txtStaffDepartment.getText().trim()
            );

            pst.setString(
                    4,
                    txtStaffPhone.getText().trim()
            );

            pst.setInt(
                    5,
                    Integer.parseInt(
                            txtStaffId.getText().trim()
                    )
            );

            int rows =
                    pst.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Staff Record Updated Successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Staff ID not found!"
                );
            }

            loadStaffData();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Update Failed!\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // STAFF - DELETE
    // =========================================================

    private void deleteStaff() {

        if (txtStaffId.getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter Staff ID to delete!"
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete " +
                                "this staff member?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION)
            return;

        try {

            String sql =
                    "DELETE FROM Staff " +
                    "WHERE staff_id = ?";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setInt(
                    1,
                    Integer.parseInt(
                            txtStaffId.getText().trim()
                    )
            );

            int rows =
                    pst.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Staff Record Deleted Successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Staff ID not found!"
                );
            }

            loadStaffData();

            clearStaff();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Delete Failed!\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // STAFF - NAVIGATION
    // =========================================================

    private void firstStaff() {

        if (staffModel.getRowCount() == 0)
            return;

        showStaff(0);
    }


    private void previousStaff() {

        int row =
                staffTable.getSelectedRow();

        if (row > 0) {

            showStaff(row - 1);

        } else if (row == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Already at the first record."
            );
        }
    }


    private void nextStaff() {

        int row =
                staffTable.getSelectedRow();

        if (row >= 0 &&
                row < staffModel.getRowCount() - 1) {

            showStaff(row + 1);

        } else if (
                row == staffModel.getRowCount() - 1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Already at the last record."
            );
        }
    }


    private void lastStaff() {

        if (staffModel.getRowCount() == 0)
            return;

        showStaff(
                staffModel.getRowCount() - 1
        );
    }


    private void showStaff(int row) {

        staffTable.setRowSelectionInterval(
                row,
                row
        );

        txtStaffId.setText(
                staffModel.getValueAt(
                        row, 0
                ).toString()
        );

        txtStaffName.setText(
                staffModel.getValueAt(
                        row, 1
                ).toString()
        );

        txtStaffRole.setText(
                staffModel.getValueAt(
                        row, 2
                ).toString()
        );

        txtStaffDepartment.setText(
                staffModel.getValueAt(
                        row, 3
                ).toString()
        );

        txtStaffPhone.setText(
                staffModel.getValueAt(
                        row, 4
                ).toString()
        );
    }


    // =========================================================
    // STAFF - CLEAR
    // =========================================================

    private void clearStaff() {

        txtStaffId.setText("");
        txtStaffName.setText("");
        txtStaffRole.setText("");
        txtStaffDepartment.setText("");
        txtStaffPhone.setText("");

        staffTable.clearSelection();

        txtStaffId.requestFocus();
    }


    // =========================================================
    // STAFF - VALIDATION
    // =========================================================

    private boolean validateStaff() {

        if (txtStaffId.getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Staff ID."
            );

            txtStaffId.requestFocus();

            return false;
        }

        try {

            Integer.parseInt(
                    txtStaffId.getText().trim()
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Staff ID must be an integer."
            );

            txtStaffId.requestFocus();

            return false;
        }


        if (txtStaffName.getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Staff Name."
            );

            txtStaffName.requestFocus();

            return false;
        }


        if (txtStaffRole.getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Staff Role."
            );

            txtStaffRole.requestFocus();

            return false;
        }


        if (txtStaffDepartment.getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Department."
            );

            txtStaffDepartment.requestFocus();

            return false;
        }

        return true;
    }


    // =========================================================
    // TABLE SEARCH FEATURE
    // =========================================================

    private JPanel createSearchPanel(JTable table, DefaultTableModel model,
                                     JScrollPane tableScrollPane, String placeholder) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JPanel searchBar = new JPanel(new BorderLayout(5, 5));
        JTextField searchField = new JTextField();
        JButton clearSearchButton = new JButton("Clear Search");
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        searchBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        searchBar.add(new JLabel("Search: "), BorderLayout.WEST);
        searchField.setToolTipText(placeholder);
        searchBar.add(searchField, BorderLayout.CENTER);
        searchBar.add(clearSearchButton, BorderLayout.EAST);

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void filterRows() {
                String query = searchField.getText().trim();
                if (query.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(query)));
                }
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterRows(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterRows(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterRows(); }
        });

        clearSearchButton.addActionListener(e -> searchField.setText(""));
        panel.add(searchBar, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        return panel;
    }


    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new HospitalManagement()
        );
    }
}
