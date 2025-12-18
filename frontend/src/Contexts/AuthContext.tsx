import { createContext, useEffect, useState } from "react";
import { getMyProfile, type UserDTO } from "../services/user.services";

interface AuthContextType {
  user: UserDTO | null;
  token: string | null;
  setToken: (token: string | null) => void;
}

export const AuthContext = createContext<AuthContextType>({
  user: null,
  token: null,
  setToken: () => {},
});

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [token, setToken] = useState<string | null>(
    localStorage.getItem("token")
  );
  const [user, setUser] = useState<UserDTO | null>(null);

  useEffect(() => {
    if (!token) return;

    getMyProfile(token)
      .then(setUser)
      .catch(() => setToken(null));
  }, [token]);

  return (
    <AuthContext.Provider value={{ user, token, setToken }}>
      {children}
    </AuthContext.Provider>
  );
};
