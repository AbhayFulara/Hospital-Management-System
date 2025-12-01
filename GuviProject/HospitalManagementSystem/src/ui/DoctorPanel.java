// package ui;

// import model.Doctor;
// import service.DoctorService;
// import util.UITheme;

// import javax.swing.*;
// import java.awt.*;
// import java.util.List;

// /**
//  * Modern doctor notes panel.
//  */
// public class DoctorPanelModern extends JPanel {

//     private final DoctorService service = new DoctorService();

//     private JList<Doctor> list;
//     private DefaultListModel<Doctor> model;
//     private JTextArea txtNotes;

//     public DoctorPanelModern() {
//         setLayout(new BorderLayout(10,10));
//         setBackground(UITheme.FORM_BG);

//         add(createHeader(), BorderLayout.NORTH);
//         add(createContent(), BorderLayout.CENTER);

//         loadDoctors();
//     }

//     private JComponent createHeader() {
//         JPanel p = new JPanel(new BorderLayout());
//         p.setBackground(UITheme.HEADER_BG);
//         p.setPreferredSize(new Dimension(100,50));

//         JLabel l = new JLabel("Doctor Notes");
//         l.setFont(UITheme.TITLE_FONT);
//         l.setForeground(Color.WHITE);
//         l.setBorder(BorderFactory.createEmptyBorder(10,20,10,10));

//         p.add(l, BorderLayout.WEST);
//         return p;
//     }

//     private JComponent createContent() {
//         model = new DefaultListModel<>();
//         list = new JList<>(model);
//         list.setFont(UITheme.TEXT_FONT);
//         list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

//         txtNotes = new JTextArea();
//         txtNotes.setFont(UITheme.TEXT_FONT);

//         JScrollPane left = new JScrollPane(list);
//         left.setBorder(BorderFactory.createTitledBorder("Doctors"));

//         JScrollPane right = new JScrollPane(txtNotes);
//         right.setBorder(BorderFactory.createTitledBorder("Notes"));

//         JButton btnSave = new JButton("Save Notes");
//         btnSave.addActionListener(e -> saveNotes());

//         JPanel rightPanel = new JPanel(new BorderLayout());
//         rightPanel.add(right, BorderLayout.CENTER);
//         rightPanel.add(btnSave, BorderLayout.SOUTH);

//         JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
//                 left,rightPanel);
//         split.setDividerLocation(250);

//         list.addListSelectionListener(e -> {
//             Doctor d = list.getSelectedValue();
//             if (d!=null) txtNotes.setText(d.getNotes());
//         });

//         return split;
//     }

//     private void loadDoctors() {
//         model.clear();
//         List<Doctor> docs = service.findAll();
//         for (Doctor d : docs) model.addElement(d);
//     }

//     private void saveNotes() {
//         Doctor d = list.getSelectedValue();
//         if (d==null) return;
//         service.updateNotes(d.getDoctorId(),txtNotes.getText());
//         JOptionPane.showMessageDialog(this,"Notes saved");
//         d.setNotes(txtNotes.getText());
//     }
// }



package ui;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class DoctorPanel extends JPanel {

    private JTextField txtId, txtName, txtSpec;
    private JTextArea txtNotes;

    public DoctorPanel() {
        setLayout(new BorderLayout(10,10));
        setBackground(Color.WHITE);

        add(createHeader(), BorderLayout.NORTH);
        add(createBody(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(140, 0, 0));
        p.setPreferredSize(new Dimension(100,55));

        JLabel title = new JLabel(
                "  Doctor Management",
                JLabel.LEFT
        );

        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setIconTextGap(10);

        p.add(title, BorderLayout.WEST);
        return p;
    }

    private JPanel createBody() {
        JPanel panel = new JPanel(new GridLayout(1,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        panel.add(createDoctorForm());
        panel.add(createNotesPanel());

        return panel;
    }

    private JPanel createDoctorForm() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Doctor Details",
                TitledBorder.LEFT,
                TitledBorder.TOP
        ));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.anchor = GridBagConstraints.WEST;

        txtId = new JTextField(8);
        txtId.setEditable(false);
        txtName = new JTextField(15);
        txtSpec = new JTextField(15);

        int r=0;
        addRow(p,c,r++,"Doctor ID:",txtId);
        addRow(p,c,r++,"Name:",txtName);
        addRow(p,c,r++,"Specialization:",txtSpec);

        JButton save = new JButton(
                "Save"
        );

        JButton clear = new JButton("Clear");

        JPanel bp = new JPanel();
        bp.add(save);
        bp.add(clear);

        c.gridx=0; 
        c.gridy=r; 
        c.gridwidth=2;
        p.add(bp,c);

        clear.addActionListener(e -> clearForm());
        return p;
    }

    private JPanel createNotesPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Doctor Notes"));

        txtNotes = new JTextArea();
        txtNotes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNotes.setLineWrap(true);

        p.add(new JScrollPane(txtNotes), BorderLayout.CENTER);
        return p;
    }

    private void addRow(JPanel p, GridBagConstraints c,
                        int y, String label, JComponent comp) {
        c.gridx=0; 
        c.gridy=y;
        p.add(new JLabel(label),c);
        c.gridx=1;
        p.add(comp,c);
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtSpec.setText("");
        txtNotes.setText("");
    }
}

