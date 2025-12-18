import { useRentals } from "../hooks/queries/useRentals";

const Rentals = () => {
  const { rentals, loading } = useRentals();

  if (loading) return <p>Loading...</p>;

  return (
    <div>
      {rentals.map((r) => (
        <div key={r.id}>
          <h3>{r.title}</h3>
          <p>{r.description}</p>
          <p>${r.price}</p>
        </div>
      ))}
    </div>
  );
};

export default Rentals;
