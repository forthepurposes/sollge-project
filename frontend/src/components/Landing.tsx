import { Link } from "react-router-dom";
import landingHouse from "../assets/images/landing_house.jpg";

export default function Landing() {
  return (
    <div className="p-6">
      <div
        className="relative bg-cover bg-center min-h-[60vh] flex items-center justify-center rounded-lg overflow-hidden"
        style={{
          backgroundImage: `url(${landingHouse})`,
        }}
      >
        <div className="absolute inset-0 bg-black/40" />

        <div className="relative z-10 max-w-2xl p-8 text-center">
          <h2 className="text-4xl font-bold mb-4 text-white">
            Find Your Next Home
          </h2>
          <p className="text-white/90 mb-6">
            Browse verified rentals from trusted real estate agents.
          </p>

          <Link
            to="/rentals"
            className="inline-block px-8 py-4 bg-transparent outline text-white font-semibold rounded-lg hover:bg-gray-200 transform hover:scale-105 hover:text-black transition-all duration-200 shadow-lg"
            //? add some extra charts or images at the bottom to fill up the
          >
            Browse Rentals
          </Link>
        </div>
      </div>
    </div>
  );
}
