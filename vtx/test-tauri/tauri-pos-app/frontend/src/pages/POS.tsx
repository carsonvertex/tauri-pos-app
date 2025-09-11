import { Card, Input } from "@mui/material";
import React, { useRef, useState } from "react";
import { NumberPad } from "../components/POS/NumberPad";

export const POS: React.FC = () => {
  const [inputValue, setInputValue] = useState("");
  const inputRef = useRef<HTMLInputElement>(null);



  return (
    <div className="grid grid-cols-2    h-full">
      <Card className="h-[vh-100] p-4">
        <h3>Cart</h3>
      </Card>

      <div className="h-full grid grid-rows-12  ">
        <div className="row-span-8">
          <Input 
            ref={inputRef}
            value={inputValue}
            onChange={(e) => setInputValue(e.target.value)}
            placeholder="Enter amount..."
            fullWidth
            sx={{ fontSize: '2rem', textAlign: 'right' }}
          />
        </div>

        <div className="row-span-4">
          <NumberPad 
            setInputValue={setInputValue}
            inputRef={inputRef}
          />
        </div>
      </div>
    </div>
  );
};
