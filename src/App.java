import com.akshaya.hyperloopbooking.HyperloopController;
import com.akshaya.hyperloopbooking.booking.BookingController;
import com.akshaya.hyperloopbooking.booking.BookingModel;
import com.akshaya.hyperloopbooking.booking.BookingView;
import com.akshaya.hyperloopbooking.booking.pricing.AgeDiscountPricingStrategy;
import com.akshaya.hyperloopbooking.booking.pricing.PricingStrategy;
import com.akshaya.hyperloopbooking.passenger.PassengerController;
import com.akshaya.hyperloopbooking.passenger.PassengerModel;
import com.akshaya.hyperloopbooking.passenger.PassengerRepo;
import com.akshaya.hyperloopbooking.passenger.PassengerView;
import com.akshaya.hyperloopbooking.route.RouteController;
import com.akshaya.hyperloopbooking.route.RouteModel;
import com.akshaya.hyperloopbooking.route.RouteView;
import com.akshaya.hyperloopbooking.route.RoutesRepo;

public class App {
    public static void main(String[] args) throws Exception {
        RoutesRepo routesRepo = RoutesRepo.getInstance();
        RouteModel routeModel = new RouteModel(routesRepo);
        RouteView routeView = new RouteView();
        RouteController routeController = new RouteController(routeModel, routeView);

        PassengerRepo passengerRepo = PassengerRepo.getInstance();
        PassengerModel passengerModel = new PassengerModel(passengerRepo);
        PassengerView passengerView = new PassengerView();
        PassengerController passengerController = new PassengerController(
                passengerModel, 
                passengerView, 
                routeController
        );

        BookingView bookingView = new BookingView();
        PricingStrategy pricingStrategy = new AgeDiscountPricingStrategy();
        BookingModel bookingModel = new BookingModel(
                passengerController, 
                routeController, 
                pricingStrategy
        );
        BookingController bookingController = new BookingController(
                bookingModel, 
                bookingView
        );

        HyperloopController hyperloopController = new HyperloopController(
                routeController, 
                passengerController, 
                bookingController
        );

        hyperloopController.start();
    }
}
