import { North, South } from "@mui/icons-material";
import { Card, CardActionArea } from "@mui/material";
import React from "react";
import { NumberPad } from "../components/POS/NumberPad";

export const POS: React.FC = () => {
  return (
    <div className="grid grid-cols-2    h-full">
      <Card className="h-[vh-100] p-4">
        <h3>Cart</h3>
      </Card>

      <div className="h-full grid grid-rows-12  ">
        <div className="row-span-8">12</div>

        <div className="row-span-4">
          <NumberPad />
        </div>
      </div>
    </div>
  );
};
