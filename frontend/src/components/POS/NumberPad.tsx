import { Backspace, North, South } from "@mui/icons-material";
import { Card, CardActionArea } from "@mui/material";

export function NumberPad() {
  const handleNumberClick = (number: string) => {
    console.log(number);
  };
  const numberPad = [
    "7",
    "8",
    "9",
    "4",
    "5",
    "6",
    "1",
    "2",
    "3",
    "0",
    ".",
    "00",
  ];
  return (
    <div className="grid grid-cols-6 h-full  ">
      {/* Arrow Pad */}
      <div className="col-span-1  border border-gray-300 grid grid-rows-2">
        <Card className="row-span-1 border border-gray-300">
          <CardActionArea className="w-full h-full flex items-center justify-center">
            <span className="text-2xl flex items-center justify-center">
              <North />
            </span>
          </CardActionArea>
        </Card>
        <Card className="row-span-1 border border-gray-300">
          <CardActionArea className="w-full h-full flex items-center justify-center">
            <span className="text-2xl flex items-center justify-center">
              <South />
            </span>
          </CardActionArea>
        </Card>
      </div>

      {/* Number Pad */}
      <div className="col-span-3    grid grid-cols-3">
        {numberPad.map((number) => (
          <Card className="flex items-center justify-center border border-gray-300">
            <CardActionArea
              onClick={() => handleNumberClick(number)}
              className="w-full h-full flex items-center justify-center"
            >
              <span className="text-2xl flex items-center justify-center">
                {number}
              </span>
            </CardActionArea>
          </Card>
        ))}
      </div>

      {/* Action Pad */}
      <div className="col-span-2  border border-gray-300 grid grid-cols-2">
        <Card className="border border-gray-300">
          <CardActionArea className="w-full h-full flex items-center justify-center">
            <span className="text-2xl flex items-center justify-center">
              <Backspace />{" "}
            </span>
          </CardActionArea>
        </Card>
        <Card className="border border-gray-300">
          <CardActionArea className="w-full h-full flex items-center justify-center">
            <span className="text-2xl flex items-center justify-center">
              {"Esc"}
            </span>
          </CardActionArea>
        </Card>
        <Card className="col-span-2 border border-gray-300">
          <CardActionArea className="w-full h-full flex items-center justify-center">
            <span className="text-2xl flex items-center justify-center">
              {"Enter"}
            </span>
          </CardActionArea>
        </Card>
      </div>
    </div>
  );
}
