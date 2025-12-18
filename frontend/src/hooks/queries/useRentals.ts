import { useEffect, useState } from "react";
import {
  getRentals,
  type RentalResponseDTO,
} from "../../services/rental.services";

export const useRentals = (page = 0, size = 10) => {
  const [rentals, setRentals] = useState<RentalResponseDTO[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getRentals({ page, size })
      .then(setRentals)
      .finally(() => setLoading(false));
  }, [page, size]);

  return { rentals, loading };
};
