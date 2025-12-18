import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import {
  getRentalById,
  type RentalResponseDTO,
} from "../services/rental.services";

export default function RentalDetail() {
  const { id } = useParams();
  const [rental, setRental] = useState<RentalResponseDTO | null>(null);

  useEffect(() => {
    if (id) getRentalById(id).then(setRental);
  }, [id]);

  if (!rental)
    return <div className="py-24 text-center text-gray-500">Loading…</div>;

  return (
    <main className="mx-auto max-w-4xl px-6 py-12">
      <div className="mb-6 aspect-video rounded-xl bg-gray-100" />

      <h1 className="text-3xl font-semibold">{rental.title}</h1>

      <p className="mt-4 text-gray-700">{rental.description}</p>

      <div className="mt-6 flex items-center gap-6">
        <span className="text-2xl font-bold">${rental.price}</span>
        <span className="text-sm text-gray-500">{rental.views} views</span>
      </div>
    </main>
  );
}
