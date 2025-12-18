import { useAuth } from "../hooks/useAuth";

export default function Profile() {
  const { user } = useAuth();

  if (!user) return <div className="py-24 text-center">Not authenticated</div>;

  return (
    <main className="mx-auto max-w-3xl px-6 py-12">
      <div className="rounded-xl border bg-white p-6">
        <h2 className="text-xl font-semibold">Profile</h2>

        <div className="mt-4 space-y-2 text-sm text-gray-700">
          <p>
            <span className="font-medium">Username:</span> {user.username}
          </p>
        </div>
      </div>
    </main>
  );
}
