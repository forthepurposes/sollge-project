// import { Routes, Route, BrowserRouter } from "react-router-dom";
// import Navbar from "./components/Navbar";
// import Landing from "./components/Landing";
// import Login from "./components/Login";
// import Register from "./components/Register";
// import Profile from "./pages/Profile";
// import Rentals from "./pages/Rentals";
// import RentalDetail from "./pages/RentalDetail";
// import DashboardCustomer from "./components/Layouts/CustomerLayout";
// import DashboardAgent from "./components/Layouts/AgentLayout";

// //   return <div className="bg-cyan-900 min-h-screen"></div>;

// function App() {
//   return (
//     <BrowserRouter>
//     <div>
//       <Navbar />
//       <main className="min-h-screen bg-[#dfdfdf] ">
//         <Routes>
//           <Route path="/" element={<Landing />} />
//           <Route path="/login" element={<Login />} />
//           <Route path="/register" element={<Register />} />
//           <Route path="/rentals" element={<Rentals />} />
//           <Route path="/rentals/:id" element={<RentalDetail />} />
//           <Route path="/profile" element={<Profile />} />
//           <Route path="/dashboard/agent" element={<DashboardAgent />} />
//           <Route path="/dashboard/customer" element={<DashboardCustomer />} />
//         </Routes>
//       </main>
//     </div>
//     </BrowserRouter>
//   );
// }

// export default App;

// src/App.tsx
import { Routes, Route, BrowserRouter } from "react-router-dom";
import Navbar from "./components/Navbar";
import Landing from "./components/Landing";
import Login from "./components/Login";
import Register from "./components/Register";
import Profile from "./pages/Profile";
import Rentals from "./pages/Rentals";
import RentalDetail from "./pages/RentalDetail";
import { AuthProvider } from "./Contexts/AuthContext";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <div>
          <Navbar />
          <main className="min-h-screen bg-[#dfdfdf] ">
            <Routes>
              <Route path="/" element={<Landing />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route path="/rentals" element={<Rentals />} />
              <Route path="/rentals/:id" element={<RentalDetail />} />
              <Route path="/profile" element={<Profile />} />
            </Routes>
          </main>
        </div>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
