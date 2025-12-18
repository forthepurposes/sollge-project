import { Link } from "react-router-dom";
export default function Navbar() {
  return (
    <nav className="bg-[#292929] shadow px-6 py-4 mx-auto flex justify-between">
      <Link
        to="/"
        className="text-white text-2xl font-bold hover:text-gray-300 tracking-wider"
      >
        Sollge
      </Link>
      <div className="text-white text-lg space-x-4">
        <Link to="/" className="hover:text-gray-300">
          Home
        </Link>
        <Link to="/rentals" className="hover:text-gray-300">
          Rentals
        </Link>
        <Link to="/login" className="hover:text-gray-300">
          Profile
        </Link>
      </div>
    </nav>
  );
}
