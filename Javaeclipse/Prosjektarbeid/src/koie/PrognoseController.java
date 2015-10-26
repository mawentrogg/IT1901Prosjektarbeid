package koie;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.PreparedStatement;
import com.sun.crypto.provider.RSACipher;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import tools.DBTools;

public class PrognoseController {
	
	
	
	@FXML
	ComboBox<String> cabindropdwn;
	@FXML
	Button loadBtn;
	@FXML
	Pane chartcontent; 
	private woodlogPrognosis log;
	private String lastCabin = "";
	private String Username;

	public void initialize() throws Exception {

		// Her vil vi få inn en liste -fra databasen- med navn over alle koiene,
		// slik som nedenfor
		List<String> list = getData();
		cabindropdwn.getItems().addAll(list);
	}

	
	String getCabinName() {
		return cabindropdwn.getValue();
	}
	
	
	@FXML int getCabinID(){
		
		try {
			String tekst = "SELECT CabinID FROM Cabin WHERE Name='"+getCabinName()+"'";
			Connection con = DBTools.quickConnect();
			PreparedStatement statement = (PreparedStatement) con.prepareStatement(tekst);
			ResultSet result = statement.executeQuery();
			int k = 0;
			while(result.next()){
				k = result.getInt("CabinID");
			}
			return k;
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 0; 
	}
		
		

	@FXML
	void displayChart() throws IOException {
		System.out.println(getCabinID());
		ObservableList<XYChart.Series<Integer, Integer>> lineChartData = FXCollections.observableArrayList();

		if (!lastCabin.equals(getCabinName())) {

			chartcontent.getChildren().clear();
			LineChart<Integer, Integer> graph = FXMLLoader.load(getClass().getResource("Chart.fxml"));


			try{
				ArrayList<Map> mapList = log.woodlogPrognosisProcess(getCabinID());				
				Map<Integer, Integer> map1 = mapList.get(0);
				Map<Integer, Integer> map2 = mapList.get(1);
				lineChartData.add(addSeries(map1));
				lineChartData.add(addSeries(map2));
				lineChartData.get(0).setName("Nylig historikk");
				lineChartData.get(1).setName("Prognose");
				graph.setData(lineChartData);
				graph.setTitle("Vedprognose for " + getCabinName());
				chartcontent.getChildren().add(graph);
				lastCabin = getCabinName();	
			}catch(IndexOutOfBoundsException|NullPointerException e1){
				graph.setTitle("Ingen historikk");
			}
			
		}
	}

	private LineChart.Series<Integer, Integer> addSeries(Map<Integer, Integer> map) {
		LineChart.Series<Integer, Integer> series = new LineChart.Series<Integer, Integer>();
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			int i = entry.getKey();
			int j = entry.getValue();
			series.getData().add(new XYChart.Data<Integer, Integer>(i, j));
		}
		return series;

	}
	public ArrayList<String> getData() throws Exception{
		
		try {
			Connection con = DBTools.quickConnect();
			PreparedStatement statement = (PreparedStatement) con.prepareStatement("SELECT Name FROM Cabin");
			ResultSet result = statement.executeQuery();
			ArrayList<String> array = new ArrayList<String>();
			while (result.next()){
				array.add(result.getString("Name"));
			}
			return array;
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null; 
	}
	


	public void setUsername(String username) {
		this.Username = username;
		
	}

}
