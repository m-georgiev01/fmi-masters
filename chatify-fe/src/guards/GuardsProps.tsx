import { ReactNode } from "react";
import { DefaultProps } from "../models/DefaultProps";

export interface GuardProps extends DefaultProps {
    children: ReactNode;
  }