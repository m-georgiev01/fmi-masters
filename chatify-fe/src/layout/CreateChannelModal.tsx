import React from "react";
import { DefaultProps } from "../models/DefaultProps";
import { observer } from "mobx-react";
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material";

@observer
export class CreateChannelModal extends React.Component<DefaultProps> {
    render(): React.ReactNode {
        const {isCreateModalOpen, changeIsCreateModalOpen, createChannelInput, setCreateChannelInput, createChannel} = this.props.state;
        return(
            <Dialog open={isCreateModalOpen} sx={{textAlign: "center"}} onClose={() => changeIsCreateModalOpen(false)}>
                <DialogTitle color="#1E293B">Create Channel</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Channel Name"
                        type="text"
                        fullWidth
                        variant="outlined"
                        value={createChannelInput}
                        onChange={(e) => setCreateChannelInput(e.target.value)}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => changeIsCreateModalOpen(false)} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={createChannel} color="primary">
                        Create
                    </Button>
                </DialogActions>
            </Dialog>
        )
    }
}