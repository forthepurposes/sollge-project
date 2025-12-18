import { userApi } from "./api";

export interface UserDTO {
  id: number;
  username: string;
}

export interface AuthResponseDTO {
  token: string;
}

/** LOGIN */
export const login = async (
  username: string,
  password: string
): Promise<AuthResponseDTO> => {
  const res = await userApi.post<AuthResponseDTO>("/users/login", {
    username,
    password,
  });
  return res.data;
};

/** REGISTER */
export const register = async (
  username: string,
  email: string,
  password: string
): Promise<UserDTO> => {
  const res = await userApi.post<UserDTO>("/users/register", {
    username,
    email,
    password,
  });
  return res.data;
};

/** CURRENT USER PROFILE (/users/me) */
export const getMyProfile = async (token: string): Promise<UserDTO> => {
  const res = await userApi.get<UserDTO>("/users/me", {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return res.data;
};

/** USER BY ID */
export const getUserById = async (id: number): Promise<UserDTO> => {
  const res = await userApi.get<UserDTO>("/users", {
    params: { id },
  });
  return res.data;
};
