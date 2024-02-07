package businesslogic.event;

import businesslogic.menu.Menu;
import businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Map;

public class ServiceInfo implements EventItemInfo {
    private int id;
    private String name;
    private Date date;
    private Time timeStart;
    private Time timeEnd;
    private int participants;
    private Menu approvedMenu;
    private static Map<Integer, ServiceInfo> loadedServices = FXCollections.observableHashMap();
    public ServiceInfo(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setApprovedMenu(Menu m) { this.approvedMenu = m; }
    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return name + ": " + date + " (" + timeStart + "-" + timeEnd + "), " + participants + " pp.";
    }

    // STATIC METHODS FOR PERSISTENCE

    public static ObservableList<ServiceInfo> loadServiceInfoForEvent(int event_id) {
        ObservableList<ServiceInfo> result = FXCollections.observableArrayList();
        String query = "SELECT id, name, service_date, time_start, time_end, expected_participants " +
                "FROM Services WHERE event_id = " + event_id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String s = rs.getString("name");
                ServiceInfo serv = new ServiceInfo(s);
                serv.id = rs.getInt("id");
                serv.date = rs.getDate("service_date");
                serv.timeStart = rs.getTime("time_start");
                serv.timeEnd = rs.getTime("time_end");
                serv.participants = rs.getInt("expected_participants");
                result.add(serv);
            }
        });

        return result;
    }

    public Menu getApprovedMenu() { return this.approvedMenu; }
    public static ObservableList<ServiceInfo> loadServicesForEvent(int eventId) {
        if (EventInfo.isLoadedEvent(eventId)) {
            EventInfo ev = EventInfo.getLoadedEvent(eventId);
            return ev.getServices();
        }

        ObservableList<ServiceInfo> result = FXCollections.observableArrayList();
        String query = "SELECT id, name, service_date, time_start, time_end, expected_participants, approved_menu_id " +
                "FROM Services WHERE event_id = " + eventId;
        ArrayList<Integer> approvedMenuIds = new ArrayList<>();
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String name = rs.getString("name");
                ServiceInfo serv = new ServiceInfo(name);
                serv.id = rs.getInt("id");
                serv.date = rs.getDate("service_date");
                serv.timeStart = rs.getTime("time_start");
                serv.timeEnd = rs.getTime("time_end");
                serv.participants = rs.getInt("expected_participants");
                approvedMenuIds.add(rs.getInt("approved_menu_id"));
                result.add(serv);
            }
        });
        for (int i = 0; i < result.size(); i++) {
            ServiceInfo s = result.get(i);
            int menuId = approvedMenuIds.get(i);
            Menu menuApproved = (menuId > 0) ? Menu.loadMenuById(menuId) : null;
            s.setApprovedMenu((menuId > 0) ? menuApproved : null);
            loadedServices.put(s.id, s);
        }
        return result;
    }
}
