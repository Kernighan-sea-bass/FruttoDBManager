package FruttoSwing;

import Negozietti.DAO.FruttoDAO;
import Negozietti.Frutto;
import Negozietti.Stagionalita;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class FruttoNew extends JDialog implements ActionListener {

    private JTextField txtId = null;
    private JTextField txtName = null;
    private JComboBox<Stagionalita> cmbStagionalita = null;
    private JTextField txtCosto = null;
    private final String patternFloat = "^(([0-9]*)|(([0-9]*)\\.([0-9]*)))$";
    private JButton btnNew = null;
    private ArrayList<Object> frutti = null;

    public FruttoNew(ArrayList<Object> frutti,Component frmRelated){

        setTitle("Frutto new");
        setSize(400, 300);      //se passo un solo parametro setta width e height allo stesso valore in pixel
        setLocationRelativeTo(frmRelated);       //null = centra in base al size, centro dello schermo

        setModal(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.frutti = frutti;
        initUI();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNew) {

            if (checkField() == true) {

                String nome = txtName.getText();
                Stagionalita stagionalita = (Stagionalita) cmbStagionalita.getSelectedItem();
                float costo = Float.parseFloat(txtCosto.getText());
                frutti.add(new Frutto(nome, stagionalita, (int)costo));
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));       //close the detail page after the update
            }
        }
    }

    public boolean checkField(){

        if(txtName.getText().isEmpty()){

            txtName.setBackground(Color.RED);
            txtName.setForeground(Color.WHITE);
            return false;
        }else{

            txtName.setBackground(Color.WHITE);
            txtName.setForeground(Color.BLACK);
        }

        if(cmbStagionalita.getSelectedIndex() < 0){

            cmbStagionalita.setBackground(Color.RED);
            cmbStagionalita.setForeground(Color.WHITE);
            return false;
        }else{

            cmbStagionalita.setBackground(Color.WHITE);
            cmbStagionalita.setForeground(Color.BLACK);
        }

        if(!isPriceValid() || txtCosto.getText().isEmpty()){

            txtCosto.setBackground(Color.RED);
            txtCosto.setForeground(Color.WHITE);
            return false;
        }else{

            txtCosto.setBackground(Color.WHITE);
            txtCosto.setForeground(Color.BLACK);
        }
        return true;
    }
    private boolean isPriceValid(){
        String price = txtCosto.getText();
        return price.matches(patternFloat);
    }
    public void initUI(){

        JLabel lblId = new JLabel("id: ");
        this.txtId = new JTextField(7);
        txtId.setEnabled(false);
        JPanel pnlId = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlId.add(lblId);
        pnlId.add(txtId);

        JLabel lblName = new JLabel("name: ");
        this.txtName = new JTextField(20);
        JPanel pnlName = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlName.add(lblName);
        pnlName.add(txtName);

        JLabel lblStagionalita = new JLabel("stagionalita: ");
        this.cmbStagionalita = new JComboBox<>(Stagionalita.values());

        JPanel pnlStagionalita = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlStagionalita.add(lblStagionalita);
        pnlStagionalita.add(cmbStagionalita);

        JLabel lblCosto = new JLabel("costo: ");
        this.txtCosto = new JTextField(10);
        JPanel pnlCosto = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlCosto.add(lblCosto);
        pnlCosto.add(txtCosto);
        JPanel pnlCenter = new JPanel(new GridLayout(4,1));

        pnlCenter.add(pnlId);
        pnlCenter.add(pnlName);
        pnlCenter.add(pnlStagionalita);
        pnlCenter.add(pnlCosto);

        add(pnlCenter);     //add() ereditato da JFrame

        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnNew = new JButton("new");
        btnNew.addActionListener(this);

        pnlSouth.add(btnNew);

        add(pnlSouth, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {

        Runnable r = new Runnable() {       //classe anonima
            @Override
            public void run() {

                new FruttoNew(null, null);
            }
        };
        SwingUtilities.invokeLater(r);
    }
}
