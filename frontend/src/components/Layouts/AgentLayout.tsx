export default function DashboardAgent() {
  return (
    <>
      <h3 className="text-xl font-bold mb-4">Your Listings</h3>
      <ul className="space-y-3">
        <li className="border p-4 rounded flex justify-between">
          <span>Modern Apartment</span>
          <span className="text-green-600">Available</span>
        </li>
        <li className="border p-4 rounded flex justify-between">
          <span>Family House</span>
          <span className="text-red-600">Rented</span>
        </li>
      </ul>
    </>
  );
}
