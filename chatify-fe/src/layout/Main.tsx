import React from "react";
import { DefaultProps } from "../models/DefaultProps";
import { Box } from "@mui/material";
import { Sidebar } from "./Sidebar";
import { Channel } from "./Channel";
import { ChannelInfo } from "./ChannelInfo";
import { CreateChannelModal } from "./CreateChannelModal";

export class Main extends React.Component<DefaultProps> {
    render(): React.ReactNode {
        return(
            <Box sx={{ width: "100%", flexGrow: "1", display: "flex"}}>
                <Sidebar state={this.props.state} />
                <Channel state={this.props.state} />
                <ChannelInfo state={this.props.state} />
                <CreateChannelModal state={this.props.state} />
            </Box>
        )
    }
}