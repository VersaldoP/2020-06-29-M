package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Adiacenze;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;
import javafx.beans.binding.MapBinding;

public class ImdbDAO {
	
	public void listArchi(Map<Integer,Director>map, int anno,List<Adiacenze>rs){
		String sql = "SELECT md1.director_id as id1,md2.director_id as id2,COUNT( r1.actor_id) AS peso "
				+ "FROM movies_directors md1,movies_directors md2 ,roles r1,roles r2,movies m1,movies m2 "
				+ "WHERE md1.director_id> md2.director_id "
				+ " AND md1.movie_id= m1.id "
				+ " AND m1.year =? AND m2.year =m1.year "
				+ " AND md2.movie_id= m2.id "
				+ "AND r1.movie_id=m1.id "
				+ "AND r2.movie_id=m2.id "
				+ "AND r1.actor_id=r2.actor_id "
				+ "GROUP BY md1.director_id ,md2.director_id "
				+ "HAVING COUNT( r1.actor_id)>0";
//		List<Adiacenze> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Director d1 = map.get(res.getInt("id1"));
				Director d2 = map.get(res.getInt("id2"));
				if(d1!=null&&d2!=null) {
					rs.add(new Adiacenze(d1,d2,res.getInt("peso")));
				}
				
				
				
			}
			conn.close();
			return ;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return ;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public Map<Integer, Director> listAllDirectors(int anno){
		String sql = "SELECT distinct d.id AS id ,d.first_name AS fn, d.last_name AS ln "
				+ "from directors d,movies m,movies_directors md "
				+ "WHERE d.id=md.director_id "
				+ "AND m.id=md.movie_id "
				+ "AND m.year =?";
		Map<Integer,Director> result = new HashMap<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("fn"), res.getString("ln"));
				
				result.put(res.getInt("id"),director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	
}
