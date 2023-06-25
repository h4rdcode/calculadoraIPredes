import java.awt.EventQueue;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.SystemColor;
import javax.swing.text.AttributeSet;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.lang.Integer;

public class Calculadora {

	public JFrame frame;
	public JTextField ipField1, ipField2, ipField3, ipField4;
	public JTextField maskField1, maskField2, maskField3, maskField4, srField, cidrField, hostsField;
	public String ip1Class, ip2Class, ip3Class, ip4Class, ipClass;
	public String maskField4Value;
	public JLabel lblNewLabel_2;
	public JLabel lblNewLabel_3;
	public JLabel lblNewLabel_4;
	public JLabel lblNewLabel_5;
	public JLabel lblNewLabel_6;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calculadora window = new Calculadora();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */

	public Calculadora() {
		initialize();
	}

	public void addNumberOnlyFilter(JTextField textField) {
		Document document = textField.getDocument();
		((AbstractDocument) document).setDocumentFilter(new DocumentFilter() {

			@Override
			public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs)
					throws BadLocationException {
				if (text.matches("\\d*")) {
					super.insertString(fb, offset, text, attrs);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				if (text.matches("\\d*")) {
					super.replace(fb, offset, length, text, attrs);
				}
			}
		});
	}

	public void validateFields() {
		String ip1Text = ipField1.getText();
		String ip2Text = ipField2.getText();
		String ip3Text = ipField3.getText();
		String ip4Text = ipField4.getText();
		String mask1Text = maskField1.getText();
		String mask2Text = maskField2.getText();
		String mask3Text = maskField3.getText();
		String mask4Text = maskField4.getText();

		if (!ip1Text.isEmpty()) {
			int ip1 = Integer.parseInt(ip1Text);
			if (ip1 > 255) {
				ipField1.setText("");
				showDialog("Erro: Valor inválido para o primeiro octeto do IP.");
			}
		}

		if (!ip2Text.isEmpty()) {
			int ip2 = Integer.parseInt(ip2Text);
			if (ip2 > 255) {
				ipField2.setText("");
				showDialog("Erro: Valor inválido para o segundo octeto do IP.");
			}
		}

		if (!ip3Text.isEmpty()) {
			int ip3 = Integer.parseInt(ip3Text);
			if (ip3 > 255) {
				ipField3.setText("");
				showDialog("Erro: Valor inválido para o terceiro octeto do IP.");
			}
		}

		if (!ip4Text.isEmpty()) {
			int ip4 = Integer.parseInt(ip4Text);
			if (ip4 > 255) {
				ipField4.setText("");
				showDialog("Erro: Valor inválido para o quarto octeto do IP.");
			}
		}

		if (!mask1Text.isEmpty()) {
			int mask1 = Integer.parseInt(mask1Text);
			if (mask1 > 255) {
				maskField1.setText("");
				showDialog("Erro: Valor inválido para o primeiro octeto da mascara");
			}
		}

		if (!mask2Text.isEmpty()) {
			int mask2 = Integer.parseInt(mask2Text);
			if (mask2 > 255) {
				maskField2.setText("");
				showDialog("Erro: Valor inválido para o segundo octeto da mascara");
			}
		}

		if (!mask3Text.isEmpty()) {
			int mask3 = Integer.parseInt(mask3Text);
			if (mask3 > 255) {
				maskField3.setText("");
				showDialog("Erro: Valor inválido para o terceiro octeto da mascara");
			}
		}

		if (!mask4Text.isEmpty()) {
			int mask4 = Integer.parseInt(mask4Text);
			if (mask4 > 255) {
				maskField4.setText("");
				showDialog("Erro: Valor inválido para o quarto octeto da mascara");
			}
		}
	}

	public void showDialog(String message) {
		JOptionPane.showMessageDialog(frame, message, "Erro", JOptionPane.ERROR_MESSAGE);
	}

	public void updateVarIP() {
		ip1Class = ipField1.getText();
		ip2Class = ipField2.getText();
		ip3Class = ipField3.getText();
		ip4Class = ipField4.getText();
	}

	public void typeClass() {
		updateVarIP();
		if (Integer.parseInt(ip1Class) >= 0 && Integer.parseInt(ip1Class) <= 127) {
			ipClass = "A";
			maskField1.setEditable(false);
			maskField1.setText("255");
			maskField2.setEditable(true);
			maskField3.setEditable(true);
			maskField4.setEditable(true);
		} else if (Integer.parseInt(ip1Class) >= 128 && Integer.parseInt(ip1Class) <= 191) {
			ipClass = "B";
			maskField1.setEditable(false);
			maskField1.setText("255");
			maskField2.setEditable(false);
			maskField2.setText("255");
			maskField3.setEditable(true);
			maskField4.setEditable(true);
		} else if (Integer.parseInt(ip1Class) >= 192 && Integer.parseInt(ip1Class) <= 223) {
			ipClass = "C";
			maskField1.setEditable(false);
			maskField1.setText("255");
			maskField2.setEditable(false);
			maskField2.setText("255");
			maskField3.setEditable(false);
			maskField3.setText("255");
			maskField4.setEditable(true);
		}

	}

	public void calculateMaskField() {
		if (ipClass.equals("C")) {
			int ip4 = Integer.parseInt(ip4Class);

			int bits = 0;
			int maskValue = ip4;
			for (int i = 7; i >= 0; i--) {
				int bit = (maskValue >> i) & 1;
				if (bit == 0) {
					bits++;
				} else {
					break;
				}
			}

			int maskField4Value = 256 - (int) Math.pow(2, bits);
			maskField4.setText(Integer.toString(maskField4Value));

			int cidrValue = 32 - bits;
			cidrField.setText(Integer.toString(cidrValue));

			int numSubnets = (int) Math.pow(2, 8 - bits);
			srField.setText(Integer.toString(numSubnets));

			int numHosts = (int) Math.pow(2, bits) - 2;
			hostsField.setText(Integer.toString(numHosts));
			if (ip4 == 255) {
				numHosts = 0;
				hostsField.setText(Integer.toString(numHosts));
			}
			if (ip4 == 0) {
				srField.setText("0");
			}
		} else if (ipClass.equals("B")) {
			int ip3 = Integer.parseInt(ip3Class);
			int ip4 = Integer.parseInt(ip4Class);

			int bits3 = 0;
			int maskValue3 = ip3;
			for (int i = 7; i >= 0; i--) {
				int bit = (maskValue3 >> i) & 1;
				if (bit == 1) {
					bits3++;
				} else {
					break;
				}
			}

			int bits4 = 0;
			int maskValue4 = ip4;
			for (int i = 7; i >= 0; i--) {
				int bit = (maskValue4 >> i) & 1;
				if (bit == 1) {
					bits4++;
				} else {
					break;
				}
			}

			int cidrValue = 16 + bits3 + bits4;
			cidrField.setText(Integer.toString(cidrValue));

			int maskField3Value = (int) (256 - Math.pow(2, 8 - bits3));
			maskField3.setText(Integer.toString(maskField3Value));

			int maskField4Value = (int) (256 - Math.pow(2, 8 - bits4));
			maskField4.setText(Integer.toString(maskField4Value));

			int numSubnets = (int) Math.pow(2, bits3);
			srField.setText(Integer.toString(numSubnets));

			int numHosts = (int) Math.pow(2, 16 - bits3 - bits4) - 2;
			hostsField.setText(Integer.toString(numHosts));
		} else if (ipClass.equals("A")) {
			int ip2 = Integer.parseInt(ip3Class);
			int ip3 = Integer.parseInt(ip3Class);
			int ip4 = Integer.parseInt(ip4Class);

			int bits2 = 0;
			int maskValue2 = ip2;
			for (int i = 7; i >= 0; i--) {
				int bit = (maskValue2 >> i) & 1;
				if (bit == 1) {
					bits2++;
				} else {
					break;
				}
			}

			int bits3 = 0;
			int maskValue3 = ip3;
			for (int i = 7; i >= 0; i--) {
				int bit = (maskValue3 >> i) & 1;
				if (bit == 1) {
					bits3++;
				} else {
					break;
				}
			}

			int bits4 = 0;
			int maskValue4 = ip4;
			for (int i = 7; i >= 0; i--) {
				int bit = (maskValue4 >> i) & 1;
				if (bit == 1) {
					bits4++;
				} else {
					break;
				}
			}

			int cidrValue = 8 + bits2 + bits3 + bits4;
			cidrField.setText(Integer.toString(cidrValue));

			int maskField2Value = (int) (256 - Math.pow(2, 8 - bits2));
			maskField2.setText(Integer.toString(maskField2Value));

			int maskField3Value = (int) (256 - Math.pow(2, 8 - bits3));
			maskField3.setText(Integer.toString(maskField3Value));

			int maskField4Value = (int) (256 - Math.pow(2, 8 - bits4));
			maskField4.setText(Integer.toString(maskField4Value));

			int numSubnets = (int) Math.pow(2, bits3);
			srField.setText(Integer.toString(numSubnets));

			int numHosts = (int) Math.pow(2, 24 - bits2 - bits3 - bits4) - 2;
			hostsField.setText(Integer.toString(numHosts));
		}
	}

	public void updateMaskField() {
		if (ipClass.equals("C")) {
			int maskValue4 = Integer.parseInt(maskField4.getText());

			if (maskValue4 == 0 || maskValue4 == 128 || maskValue4 == 192 || maskValue4 == 224 || maskValue4 == 240
					|| maskValue4 == 248 || maskValue4 == 252 || maskValue4 == 254 || maskValue4 == 255) {

				int bits = 0;
				int tempMaskValue = maskValue4;
				for (int i = 7; i >= 0; i--) {
					int bit = (tempMaskValue >> i) & 1;
					if (bit == 1) {
						bits++;
					} else {
						break;
					}
				}

				int cidrValue = 24 + bits;
				cidrField.setText(Integer.toString(cidrValue));

				int numSubnets = (int) Math.pow(2, bits);
				srField.setText(Integer.toString(numSubnets));

				int numHosts = (int) Math.pow(2, 8 - bits) - 2;
				hostsField.setText(Integer.toString(numHosts));
				if (maskValue4 == 255) {
					numHosts = 0;
					hostsField.setText(Integer.toString(numHosts));
				}
				if (maskValue4 == 0) {
					srField.setText("0");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Máscara inválida para a classe C");
			}
		}
		if (ipClass.equals("B")) {
			int maskValue3 = Integer.parseInt(maskField3.getText());
			int maskValue4 = Integer.parseInt(maskField4.getText());

			if (maskValue3 > maskValue4 && maskValue3 != 0) {
				if (maskValue3 == 0 || maskValue3 == 128 || maskValue3 == 192 || maskValue3 == 224 || maskValue3 == 240
						|| maskValue3 == 248 || maskValue3 == 252 || maskValue3 == 254 || maskValue3 == 255) {

					int bits = 0;
					int tempMaskValue = maskValue3;
					for (int i = 7; i >= 0; i--) {
						int bit = (tempMaskValue >> i) & 1;
						if (bit == 1) {
							bits++;
						} else {
							break;
						}
					}

					int cidrValue = 16 + bits;
					cidrField.setText(Integer.toString(cidrValue));

					int numSubnets = (int) Math.pow(2, +bits);
					srField.setText(Integer.toString(numSubnets));

					int numHosts = (int) Math.pow(2, 16 - bits) - 2;
					hostsField.setText(Integer.toString(numHosts));
					if (maskValue3 == 0) {
						srField.setText("0");
					}

				} else {
					JOptionPane.showMessageDialog(null, "Máscara inválida para a classe B");
				}

			}

			if (maskValue3 == 255 && maskValue4 != 0) {
				if (maskValue4 == 0 || maskValue4 == 128 || maskValue4 == 192 || maskValue4 == 224 || maskValue4 == 240
						|| maskValue4 == 248 || maskValue4 == 252 || maskValue4 == 254 || maskValue4 == 255) {

					int bits = 0;
					int tempMaskValue = maskValue4;
					for (int i = 7; i >= 0; i--) {
						int bit = (tempMaskValue >> i) & 1;
						if (bit == 1) {
							bits++;
						} else {
							break;
						}
					}

					int cidrValue = 24 + bits;
					cidrField.setText(Integer.toString(cidrValue));

					int numSubnets = (int) Math.pow(2, 8 + bits);
					srField.setText(Integer.toString(numSubnets));

					int numHosts = (int) Math.pow(2, 8 - bits) - 2;
					hostsField.setText(Integer.toString(numHosts));
					if (maskValue4 == 0) {
						srField.setText("0");
					}

				} else {
					JOptionPane.showMessageDialog(null, "Máscara inválida para a classe B");
				}
			}
		}
		if (ipClass.equals("A")) {
			int maskValue2 = Integer.parseInt(maskField2.getText());
			int maskValue3 = Integer.parseInt(maskField3.getText());
			int maskValue4 = Integer.parseInt(maskField4.getText());

			if (maskValue2 < 255 && maskValue3 >= 0) {
				if (maskValue2 == 0 || maskValue2 == 128 || maskValue2 == 192 || maskValue2 == 224 || maskValue2 == 240
						|| maskValue2 == 248 || maskValue2 == 252 || maskValue2 == 254 || maskValue2 == 255) {
					if (maskValue2 == 0) {
						srField.setText("0");
					}

					int bits = 0;
					int tempMaskValue = maskValue2;
					for (int i = 7; i >= 0; i--) {
						int bit = (tempMaskValue >> i) & 1;
						if (bit == 1) {
							bits++;
						} else {
							break;
						}

						int cidrValue = 8 + bits;
						cidrField.setText(Integer.toString(cidrValue));

						int numSubnets = (int) Math.pow(2, bits);
						srField.setText(Integer.toString(numSubnets));

						int numHosts = (int) ((Math.pow(2, 16 + (8 - bits)))) - 2;
						hostsField.setText(Integer.toString(numHosts));
					}

				} else {
					JOptionPane.showMessageDialog(null, "Máscara inválida para a classe A");
				}
			}
			if (maskValue2 == 255 && maskValue3 >= 0) {
				if (maskValue3 == 0 || maskValue3 == 128 || maskValue3 == 192 || maskValue3 == 224 || maskValue3 == 240
						|| maskValue3 == 248 || maskValue3 == 252 || maskValue3 == 254 || maskValue3 == 255) {

					int bits = 8;
					int tempMaskValue = maskValue3;
					for (int i = 7; i >= 0; i--) {
						int bit = (tempMaskValue >> i) & 1;
						if (bit == 1) {
							bits++;
						} else {
							break;
						}

						int cidrValue = 8 + bits;
						cidrField.setText(Integer.toString(cidrValue));

						int numSubnets = (int) Math.pow(2, bits);
						srField.setText(Integer.toString(numSubnets));

						int numHosts = (int) ((Math.pow(2, 16 + (8 - bits)))) - 2;
						hostsField.setText(Integer.toString(numHosts));
					}

				} else {
					JOptionPane.showMessageDialog(null, "Máscara inválida para a classe A");
				}
			}
			if (maskValue2 == 255 && maskValue3 == 255 && maskValue4 >=0) {
				if (maskValue3 == 0 || maskValue3 == 128 || maskValue3 == 192 || maskValue3 == 224 || maskValue3 == 240
						|| maskValue3 == 248 || maskValue3 == 252 || maskValue3 == 254 || maskValue3 == 255) {
					
					if (maskValue4 == 255) {
						hostsField.setText("0");
					}

					int bits = 0;
					int tempMaskValue = maskValue4;
					for (int i = 7; i >= 0; i--) {
						int bit = (tempMaskValue >> i) & 1;
						if (bit == 1) {
							bits++;
						} else {
							break;
						}

						int cidrValue = 24 + bits;
						cidrField.setText(Integer.toString(cidrValue));

						int numSubnets = (int) ((Math.pow(2, 8 + (8 + bits))));
						srField.setText(Integer.toString(numSubnets));

						int numHosts = (int) ((Math.pow(2, 8 - bits)) - 2);
						hostsField.setText(Integer.toString(numHosts));
					}

				} else {
					JOptionPane.showMessageDialog(null, "Máscara inválida para a classe A");
				}
			}

		}
	}

	public void updateFieldsCIDR() {
		if (ipClass.equals("C")) {
			int cidrValue = Integer.parseInt(cidrField.getText());

			if (cidrValue >= 24 && cidrValue <= 32) {
				int bits = 32 - cidrValue;

				int maskField4Value = 256 - (int) Math.pow(2, bits);
				maskField4.setText(Integer.toString(maskField4Value));

				int numSubnets = (int) Math.pow(2, 8 - bits);
				srField.setText(Integer.toString(numSubnets));

				int numHosts = (int) Math.pow(2, bits) - 2;
				hostsField.setText(Integer.toString(numHosts));

				if (cidrValue == 24) {
					srField.setText("0");
				}
			} else {
				JOptionPane.showMessageDialog(null, "CIDR inválido para a classe C. Intervalo válido: 24 a 32.", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		if (ipClass.equals("B")) {
			int cidrValue = Integer.parseInt(cidrField.getText());

			if (cidrValue >= 16 && cidrValue <= 24) {

				if (cidrValue == 16) {
					srField.setText("0");
				} else {

					int bits = cidrValue - 16;

					int maskField3Value = 255;
					int maskField4Value = 0;
					int numSubnets = (int) Math.pow(2, +bits);
					int numHosts = 0;

					maskField3Value = 256 - (int) Math.pow(2, 8 - bits);
					numHosts = (int) Math.pow(2, 16 - bits) - 2;

					maskField3.setText(Integer.toString(maskField3Value));
					maskField4.setText(Integer.toString(maskField4Value));
					srField.setText(Integer.toString(numSubnets));
					hostsField.setText(Integer.toString(numHosts));
				}
			} else if (cidrValue >= 25 && cidrValue <= 32) {

				int bits = cidrValue - 16;

				int maskField3Value = 255;
				int maskField4Value = 0;
				int numSubnets = (int) Math.pow(2, 8 + bits);
				int numHosts = 0;

				maskField4Value = 256 - (int) Math.pow(2, 8 - bits);
				numHosts = (int) Math.pow(2, 8 - bits) - 2;

				maskField3.setText(Integer.toString(maskField3Value));
				maskField4.setText(Integer.toString(maskField4Value));
				srField.setText(Integer.toString(numSubnets));
				hostsField.setText(Integer.toString(numHosts));

			} else {
				JOptionPane.showMessageDialog(null, "CIDR inválido para a classe B. Intervalo válido: 16 a 32.", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		if (ipClass.equals("A")) {
			int cidrValue = Integer.parseInt(cidrField.getText());

			if (cidrValue >= 8 && cidrValue <= 16) {
				if (cidrValue == 8) {
					srField.setText("0");
					maskField2.setText("0");
					maskField3.setText("0");
					maskField4.setText("0");
				} else {
					int bit = cidrValue - 8;
					int bits1 = cidrValue - 16;
					int bits = cidrValue - 24;

					int maskField2Value = 0;
					int maskField3Value = 0;
					int maskField4Value = 0;
					int numSubnets = (int) ((Math.pow(2, bit)));
					int numHosts = 0;

					maskField2Value = 256 - (int) Math.pow(2, -bits1);
					numHosts = (int) Math.pow(2, 8 - bits) - 2;

					maskField2.setText(Integer.toString(maskField2Value));
					maskField3.setText(Integer.toString(maskField3Value));
					maskField4.setText(Integer.toString(maskField4Value));
					srField.setText(Integer.toString(numSubnets));
					hostsField.setText(Integer.toString(numHosts));
				}
			} else if (cidrValue > 16 && cidrValue <= 24) {
				int bit = cidrValue - 8;
				int bits = cidrValue - 24;

				int maskField2Value = 255;
				int maskField3Value = 0;
				int maskField4Value = 0;
				int numSubnets = (int) ((Math.pow(2, bit)));
				int numHosts = 0;

				maskField3Value = 256 - (int) Math.pow(2, -bits);
				numHosts = (int) Math.pow(2, 8 - bits) - 2;

				maskField2.setText(Integer.toString(maskField2Value));
				maskField3.setText(Integer.toString(maskField3Value));
				maskField4.setText(Integer.toString(maskField4Value));
				srField.setText(Integer.toString(numSubnets));
				hostsField.setText(Integer.toString(numHosts));
			} else if (cidrValue > 24 && cidrValue <= 32) {
				int bit = cidrValue - 8;
				int bits = cidrValue - 24;

				int maskField2Value = 255;
				int maskField3Value = 255;
				int maskField4Value = 0;
				int numSubnets = (int) ((Math.pow(2, bit)));
				int numHosts = 0;

				maskField4Value = 256 - (int) Math.pow(2, 8 - bits);
				numHosts = (int) Math.pow(2, 8 - bits) - 2;

				maskField2.setText(Integer.toString(maskField2Value));
				maskField3.setText(Integer.toString(maskField3Value));
				maskField4.setText(Integer.toString(maskField4Value));
				srField.setText(Integer.toString(numSubnets));
				hostsField.setText(Integer.toString(numHosts));
			} else {
				JOptionPane.showMessageDialog(null, "CIDR inválido para a classe A . Intervalo válido: 13 a 32.",
						"Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.activeCaption);
		frame.setBounds(100, 100, 511, 321);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		ipField1 = new JTextField();
		ipField1.setBounds(163, 104, 48, 20);
		frame.getContentPane().add(ipField1);
		ipField1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				validateFields();
			}
		});

		ipField2 = new JTextField();
		ipField2.setBounds(221, 104, 48, 20);
		ipField2.setColumns(10);
		frame.getContentPane().add(ipField2);
		ipField2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				validateFields();
			}
		});

		ipField3 = new JTextField();
		ipField3.setBounds(279, 104, 48, 20);
		ipField3.setColumns(10);
		frame.getContentPane().add(ipField3);
		ipField3.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				validateFields();
			}
		});

		ipField4 = new JTextField();
		ipField4.setBounds(337, 104, 48, 20);
		ipField4.setColumns(10);
		frame.getContentPane().add(ipField4);
		ipField4.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				validateFields();
				typeClass();
				calculateMaskField();
			}
		});

		maskField1 = new JTextField();
		maskField1.setBounds(163, 135, 48, 20);
		maskField1.setColumns(10);
		frame.getContentPane().add(maskField1);
		maskField1.setEditable(false);

		maskField2 = new JTextField();
		maskField2.setBounds(222, 135, 48, 20);
		maskField2.setColumns(10);
		maskField2.setEditable(false);
		frame.getContentPane().add(maskField2);

		maskField3 = new JTextField();
		maskField3.setBounds(281, 135, 48, 20);
		maskField3.setColumns(10);
		maskField3.setEditable(false);
		frame.getContentPane().add(maskField3);

		maskField4 = new JTextField();
		maskField4.setBounds(337, 135, 48, 20);
		maskField4.setColumns(10);
		maskField4.setEditable(false);
		frame.getContentPane().add(maskField4);
		maskField4.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				updateMaskField();
				validateFields();
			}
		});

		srField = new JTextField();
		srField.setBounds(163, 197, 76, 20);
		srField.setColumns(10);
		srField.setEditable(false);
		frame.getContentPane().add(srField);

		cidrField = new JTextField();
		cidrField.setBounds(163, 166, 76, 20);
		cidrField.setColumns(10);
		frame.getContentPane().add(cidrField);
		cidrField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				updateFieldsCIDR();
			}
		});

		hostsField = new JTextField();
		hostsField.setBounds(163, 228, 76, 20);
		hostsField.setColumns(10);
		hostsField.setEditable(false);
		frame.getContentPane().add(hostsField);

		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("IP");
		lblNewJgoodiesLabel.setBounds(10, 104, 92, 20);
		frame.getContentPane().add(lblNewJgoodiesLabel);

		JLabel lblNewJgoodiesLabel_1 = DefaultComponentFactory.getInstance().createLabel("Mascara de Sub-Rede");
		lblNewJgoodiesLabel_1.setBounds(10, 135, 124, 20);
		frame.getContentPane().add(lblNewJgoodiesLabel_1);

		JLabel lblNewJgoodiesLabel_2 = DefaultComponentFactory.getInstance().createLabel("CIDR");
		lblNewJgoodiesLabel_2.setBounds(10, 169, 92, 14);
		frame.getContentPane().add(lblNewJgoodiesLabel_2);

		JLabel lblNewJgoodiesLabel_3 = DefaultComponentFactory.getInstance().createLabel("SR");
		lblNewJgoodiesLabel_3.setBounds(10, 200, 92, 14);
		frame.getContentPane().add(lblNewJgoodiesLabel_3);

		JLabel lblNewJgoodiesLabel_4 = DefaultComponentFactory.getInstance().createLabel("Hosts");
		lblNewJgoodiesLabel_4.setBounds(10, 231, 92, 14);
		frame.getContentPane().add(lblNewJgoodiesLabel_4);

		addNumberOnlyFilter(ipField1);
		addNumberOnlyFilter(ipField2);
		addNumberOnlyFilter(ipField3);
		addNumberOnlyFilter(ipField4);
		addNumberOnlyFilter(srField);
		addNumberOnlyFilter(cidrField);
		addNumberOnlyFilter(hostsField);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 495, 55);
		panel.setForeground(new Color(255, 255, 255));
		panel.setToolTipText("");
		panel.setBackground(new Color(135, 206, 250));
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Calculadora IP");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(212, 11, 94, 33);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel(".");
		lblNewLabel_1.setBounds(211, 106, 11, 14);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel(".");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(269, 107, 11, 14);
		frame.getContentPane().add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel(".");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(327, 107, 11, 14);
		frame.getContentPane().add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel(".");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(211, 138, 11, 14);
		frame.getContentPane().add(lblNewLabel_4);

		lblNewLabel_5 = new JLabel(".");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(270, 138, 11, 14);
		frame.getContentPane().add(lblNewLabel_5);

		lblNewLabel_6 = new JLabel(".");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_6.setBounds(329, 138, 11, 14);
		frame.getContentPane().add(lblNewLabel_6);
		validateFields();

	}
}
