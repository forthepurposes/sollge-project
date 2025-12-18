import { rentalApi } from "./api";

export interface RentalResponseDTO {
  id: string;
  title: string;
  description: string;
  imagesLinks: string[];
  ownerId: number;
  price: number;
  views: number;
  createdAt: string;
}

export interface Pageable {
  page: number;
  size: number;
  sort?: string[];
}

/** GET RENTALS (paginated) */
export const getRentals = async (
  pageable: Pageable
): Promise<RentalResponseDTO[]> => {
  const res = await rentalApi.get<RentalResponseDTO[]>("/rentals", {
    params: pageable,
  });
  return res.data;
};

/** GET RENTAL BY ID */
export const getRentalById = async (id: string): Promise<RentalResponseDTO> => {
  const res = await rentalApi.get<RentalResponseDTO>(`/rentals/${id}`);
  return res.data;
};

/** CREATE RENTAL */
export const submitRental = async (
  userId: number,
  data: FormData
): Promise<RentalResponseDTO> => {
  const res = await rentalApi.post<RentalResponseDTO>("/rentals", data, {
    headers: {
      "X-User-Id": userId,
      "Content-Type": "multipart/form-data",
    },
  });
  return res.data;
};
