package data;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/** @see http://stackoverflow.com/questions/5048852 */
public class TimeSeriesChart extends ApplicationFrame {

	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Dynamic Series";
	private static final String START = "Start";
	private static final String STOP = "Stop";
	private static final float MINMAX = 100;
	private static final int COUNT = 5000;
	private static final int FAST = 1;
	private static final int SLOW = FAST;
	private static final Random random = new Random();
	private Timer timer;
	private DataCenter dc;

	public TimeSeriesChart(String title) {
		super(title);

		dc = DataCenter.getInstance();
		try {
			dc.connect();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//        dc.getConnect();

		final TimeSeriesCollection dataset;
		final TimeSeries sensorSeries;
		sensorSeries = new TimeSeries("name", Millisecond.class);
		sensorSeries.setMaximumItemAge(10000);
		//sensorSeries.setMaximumItemCount(1000000);
		dataset = new TimeSeriesCollection();
		dataset.addSeries(sensorSeries);
		//dataset.setTimeBase(new Millisecond());
		//dataset.addSeries(gaussianData(), 0, "data");
		JFreeChart chart = createChart(dataset);

		final JButton run = new JButton(STOP);
		run.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String cmd = e.getActionCommand();
				if (STOP.equals(cmd)) {
					timer.stop();
					run.setText(START);
				} else {
					timer.start();
					run.setText(STOP);
				}
			}
		});

		final JComboBox<String> combo = new JComboBox<>();
		combo.addItem("Fast");
		combo.addItem("Slow");
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if ("Fast".equals(combo.getSelectedItem())) {
					timer.setDelay(FAST);
				} else {
					timer.setDelay(SLOW);
				}
			}
		});

		this.add(new ChartPanel(chart), BorderLayout.CENTER);
		JPanel btnPanel = new JPanel(new FlowLayout());
		btnPanel.add(run);
		btnPanel.add(combo);
		this.add(btnPanel, BorderLayout.SOUTH);
		timer = new Timer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//dataset.advanceTime();
				//dataset.appendData(newData);
				ConnectData cd = dc.getData().getAndRemoveLast();
				if(cd != null){

					Millisecond ms = new Millisecond(cd.getTimeStamp());
					sensorSeries.addOrUpdate(ms, cd.getValueMap().get("data"));
//					ms.next();

				}
				//sensorSeries.removeAgedItems(false);
				// dataset.removeAgedItems(false);
			}
		});
	}

	private JFreeChart createChart(final XYDataset dataset) {
		final JFreeChart result = ChartFactory.createTimeSeriesChart(
				TITLE, "hh:mm:ss.SSS", "asdf", dataset, true, true, false);
		final XYPlot plot = result.getXYPlot();
		ValueAxis domain = plot.getDomainAxis();
		domain.setAutoRange(true);
		ValueAxis range = plot.getRangeAxis();
		range.setRange(-MINMAX, MINMAX);
		return result;
	}

	public void start() {
		timer.start();
	}

	public static void main(final String[] args) throws SQLException {
//		SQLConnection sqlc = new SQLConnection();
//		ResultSet rs = sqlc.select("SELECT * FROM data");
//		
//		while(rs.next()){
//			System.out.println("ID" + rs.getInt("id"));
//			System.out.println("SID" + rs.getInt("sessionid"));
//			System.out.println("V" + rs.getDouble("value"));
//			System.out.println("TS" + rs.getInt("timestamp"));
//			System.out.println();
//		}
//		
//		System.exit(0);
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				TimeSeriesChart demo = new TimeSeriesChart(TITLE);
				demo.pack();
				RefineryUtilities.centerFrameOnScreen(demo);
				demo.setVisible(true);
				demo.start();
			}
		});
	}
}