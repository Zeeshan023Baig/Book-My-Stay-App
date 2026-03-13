import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// --- Use Case 5: Booking Request (FIFO) ---
class Reservation {
    private String guestName;
    private String roomType;
    private String reservationId;

    public Reservation(String guestName, String roomType, String reservationId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.reservationId = reservationId;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getReservationId() { return reservationId; }
}

// --- Use Case 7: Add-On Service Selection ---
class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public double getCost() { return cost; }
}

class AddOnServiceManager {
    private Map<String, List<Service>> servicesByReservation = new HashMap<>();

    public void addService(String reservationId, Service service) {
        servicesByReservation.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {
        List<Service> services = servicesByReservation.get(reservationId);
        if (services == null) return 0.0;
        return services.stream().mapToDouble(Service::getCost).sum();
    }
}

// --- MAIN CLASS ---
public class HotelBookingApp {
    public static void main(String[] args) {
        // 1. Initialize Managers
        AddOnServiceManager serviceManager = new AddOnServiceManager();
        Queue<Reservation> requestQueue = new LinkedList<>();

        // 2. Create a Reservation (Single-1) as per your output requirement
        Reservation r1 = new Reservation("Abhi", "Single", "Single-1");
        requestQueue.offer(r1);

        // 3. Add Services (e.g., Breakfast: 500.0 + Spa: 1000.0 = 1500.0)
        serviceManager.addService("Single-1", new Service("Breakfast", 500.0));
        serviceManager.addService("Single-1", new Service("Spa", 1000.0));

        // 4. Process Queue and Produce the EXACT Output requested
        if (!requestQueue.isEmpty()) {
            Reservation current = requestQueue.poll();
            double totalCost = serviceManager.calculateTotalServiceCost(current.getReservationId());

            System.out.println("Add-On Service Selection");
            System.out.println("Reservation ID: " + current.getReservationId());
            System.out.println("Total Add-On Cost: " + totalCost);
        }
    }
}