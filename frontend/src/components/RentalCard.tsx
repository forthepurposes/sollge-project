import { Link } from "react-router-dom";

type Props = {
  id: string;
  title: string;
  price: number;
  imageUrl: string;
  description: string;
};

export default function RentalCard({
  id,
  title,
  price,
  imageUrl,
  description,
}: Props) {
  return (
    <Link to={`/rentals/${id}`} className="block">
      <div className="w-47.5 h-63.5 bg-white rounded-[10px] transition-all duration-500 hover:rounded-[20px] shadow-[inset_0_-3em_3em_rgba(0,0,0,0.1),0_0_0_2px_rgb(190,190,190),0.3em_0.3em_1em_rgba(0,0,0,0.3)] hover:shadow-[inset_0_-3em_3em_rgba(0,0,0,0.1),0_0_0_2px_rgb(190,190,190),0.5em_0.5em_1.5em_rgba(0,0,0,0.4)] overflow-hidden relative flex flex-col">
        <img
          src={imageUrl}
          alt={title}
          className="flex-1 w-full object-cover"
        />
        <div className="p-3 bg-white">
          <h3 className="font-semibold text-sm mb-1">{title}</h3>
          <p className="text-sm text-gray-600">{description}</p>
          <p className="text-sm text-gray-600">${price}/month</p>
        </div>
      </div>
    </Link>
  );
}
