import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { register } from "../services/user.services";

export default function Register() {
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);

  const submit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      await register(username, email, password);
      navigate("/login");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-[#dfdfdf] px-6">
      <div className="w-full max-w-md rounded-xl border-0 bg-white p-8 drop-shadow-2xl">
        <h1 className="mb-6 text-center text-2xl font-semibold">
          Create an account
        </h1>

        <form onSubmit={submit}>
          <input
            className="mb-4 w-full rounded-lg border px-4 py-2 focus:outline-none focus:ring-2 focus:ring-gray-300"
            placeholder="Username"
            required
            onChange={(e) => setUsername(e.target.value)}
          />

          <input
            type="email"
            className="mb-4 w-full rounded-lg border px-4 py-2 focus:outline-none focus:ring-2 focus:ring-gray-300"
            placeholder="Email"
            required
            onChange={(e) => setEmail(e.target.value)}
          />

          <div className="relative mb-6">
            <input
              type={showPassword ? "text" : "password"}
              className="w-full rounded-lg border px-4 py-2 pr-12 focus:outline-none focus:ring-2 focus:ring-gray-300"
              placeholder="Password"
              required
              minLength={8}
              onChange={(e) => setPassword(e.target.value)}
            />

            <button
              type="button"
              onClick={() => setShowPassword((v) => !v)}
              className="absolute inset-y-0 right-3 flex items-center text-sm text-gray-500 hover:text-gray-700"
              aria-label={showPassword ? "Hide password" : "Show password"}
            >
              {showPassword ? "Hide" : "Show"}
            </button>
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full rounded-lg bg-transparent py-2 font-medium text-black
                        outline-1 outline-gray-300
                       transition hover:bg-gray-200 disabled:opacity-50"
          >
            {loading ? "Creating account..." : "Sign up"}
          </button>
        </form>

        <p className="mt-6 text-center text-gray-600">
          Already have an account?{" "}
          <Link
            to="/login"
            className="text-blue-500 transition hover:text-blue-600"
          >
            Log in
          </Link>
        </p>
      </div>
    </div>
  );
}
