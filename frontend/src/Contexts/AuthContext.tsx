// import { createContext, useState, useEffect } from "react";

// interface AuthContextProps {
//   children: React.ReactNode;
// }

// interface AuthContextState {
//   user: any | null;
//   token: string | null;
//   login: (token: string, user: any) => void;
//   logout: () => void;
//   isAuthenticated: boolean;
// }

// const AuthContext = createContext<AuthContextState | null>(null);

// const AuthProvider: React.FC<AuthContextProps> = ({ children }) => {
//   const [user, setUser] = useState<any | null>(() => {
//     const raw = localStorage.getItem("user");
//     return raw ? JSON.parse(raw) : null;
//   });
//   const [token, setToken] = useState<string | null>(() =>
//     localStorage.getItem("token")
//   );

//   useEffect(() => {
//     if (user) localStorage.setItem("user", JSON.stringify(user));
//     else localStorage.removeItem("user");
//     if (token) localStorage.setItem("token", token);
//     else localStorage.removeItem("token");
//   }, [user, token]);

//   const login = (t: string, u: any) => {
//     setToken(t);
//     setUser(u);
//   };
//   const logout = () => {
//     setToken(null);
//     setUser(null);
//     localStorage.removeItem("token");
//     localStorage.removeItem("user");
//   };

//   const isAuthenticated = !!token;

//   return (
//     <AuthContext.Provider
//       value={{ user, token, login, logout, isAuthenticated }}
//     >
//       {children}
//     </AuthContext.Provider>
//   );
// };

// export { AuthProvider, AuthContext };
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
