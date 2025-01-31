import { observer } from "mobx-react";
import React from "react";
import { DefaultProps } from "../models/DefaultProps";
import { Box, IconButton, TextField, Typography } from "@mui/material";
import { Admin, Member, Owner } from "../models/Constants";
import PersonRemoveIcon from '@mui/icons-material/PersonRemove';
import UpgradeIcon from '@mui/icons-material/Upgrade';

@observer
export class ChannelInfo extends React.Component<DefaultProps> {
    render(): React.ReactNode {
        const {selectedChannel, channelMembers, user, removeMember, promoteToAdmin, isEditingChannel, changeIsEditingChannel, changeChannelName, editedChannelName, saveChangedChannelName, deleteChannel} = this.props.state;
        const currUserRole = channelMembers.filter(c => c.user.id === user?.id).map(cm => cm.role.name)[0];

        return (
            selectedChannel ?
            <Box minWidth="300px" sx={{display: "flex", flexDirection: "column", pt: 5, backgroundColor: "#1E293B"}}>
                <Box sx={{color:"#ffffffde", flexGrow: "1", overflow: "auto", maxHeight: "200px"}} >
                    <Typography variant="h5" color="inherit">
                        Members
                    </Typography>
                    <Box>
                        {channelMembers.map(cm => 
                            <Box key={cm.id} display="flex" justifyContent="center">
                                <Typography variant="body1">{cm.user?.username}</Typography>
                                
                                {currUserRole === Owner && cm.role.name === Member ?
                                    <>
                                        <IconButton sx={{p: "0", pl: "5px"}} onClick={() => removeMember(cm.user.id)}>
                                            <PersonRemoveIcon />
                                        </IconButton>
                                        <IconButton sx={{p: "0", pl: "5px"}} onClick={() => promoteToAdmin(cm.user.id)}>
                                            <UpgradeIcon />
                                        </IconButton>
                                    </> :
                                    <></>
                                }
                            </Box>
                        )}
                    </Box>
                </Box>


                <Box sx={{color:"#ffffffde", flexGrow: "1", maxHeight: "168px"}} >
                    <Typography variant="h5">
                        Add members
                    </Typography>
                    <Box >
                        {!this.props.state.membersSearch ? 
                            <Box display="flex" justifyContent="center" gap="10px" alignItems="center">
                                <TextField
                                    label="Members"
                                    variant="outlined"
                                    margin="normal"
                                    value={this.props.state.membersInput}
                                    onChange={(e) => this.props.state.setMembersSearchInput(e.target.value)}
                                    sx={{
                                        width: "60%",
                                        '& label.Mui-focused': {
                                            color: '#ffffffde',
                                        },
                                        '& .MuiOutlinedInput-root': {
                                            '&.Mui-focused fieldset': {
                                            borderColor: '#ffffffde',
                                            },
                                            '& fieldset': {
                                            borderColor: '#ffffffde',
                                            },
                                            '&:hover fieldset': {
                                            borderColor: '#ffffffde',
                                            },
                                        },
                                        '& .MuiInputLabel-root': {
                                            color: '#ffffffde',
                                        },
                                        '& input': {
                                            color: '#ffffffde',
                                        },
                                    }}
                                />
                                <Typography variant="body1" sx={{cursor: "pointer", ":hover": {textDecoration: "underline"}}} onClick={this.props.state.searchMembers}>Search</Typography>
                            </Box> :
                            <></>
                        }
                        
                        <Box>
                            {this.props.state.membersSearch ?
                                <Box>
                                    <Box maxHeight="100px" overflow="auto">
                                        {this.props.state.membersSearch.map((u) => 
                                            <Box>
                                                <Typography sx={{cursor: "pointer", ":hover": {textDecoration: "underline"}}} onClick={() => this.props.state.addMember(u.id)}>{u.username}</Typography>
                                            </Box>
                                        )}
                                    </Box>
                                    <Typography variant="body1" sx={{mt: 1, cursor: "pointer", ":hover": {textDecoration: "underline"}}} onClick={this.props.state.clearMembersSearch}>Cancel</Typography>
                                </Box>  :
                                <></>
                            }
                        </Box>
                    </Box>
                </Box>


                <Box sx={{color:"#ffffffde", flexGrow: "1", maxHeight: "150px"}} >
                    {currUserRole === Owner || currUserRole === Admin ?
                        <Box>
                            <Typography variant="h5" color="inherit">
                                Edit Channel
                            </Typography>
                            
                            <Box>
                                {!isEditingChannel ? 
                                    <Box sx={{display: "flex", justifyContent: "center", gap:"10px"}}>
                                        <Typography variant="body1" sx={{cursor: "pointer", ":hover": {textDecoration: "underline"}}} onClick={() => changeIsEditingChannel(true)}>Rename</Typography>
                                        {currUserRole === Owner ?
                                            <Typography variant="body1" sx={{cursor: "pointer", ":hover": {textDecoration: "underline"}}} onClick={deleteChannel}>Delete</Typography> :
                                            <></>
                                        }
                                    </Box> :
                                    <Box >
                                        <TextField
                                            label="Channel name"
                                            variant="outlined"
                                            margin="normal"
                                            value={editedChannelName}
                                            onChange={(e) => changeChannelName(e.target.value)}
                                            sx={{
                                                width: "60%",
                                                '& label.Mui-focused': {
                                                    color: '#ffffffde',
                                                },
                                                '& .MuiOutlinedInput-root': {
                                                    '&.Mui-focused fieldset': {
                                                    borderColor: '#ffffffde',
                                                    },
                                                    '& fieldset': {
                                                    borderColor: '#ffffffde',
                                                    },
                                                    '&:hover fieldset': {
                                                    borderColor: '#ffffffde',
                                                    },
                                                },
                                                '& .MuiInputLabel-root': {
                                                    color: '#ffffffde',
                                                },
                                                '& input': {
                                                    color: '#ffffffde',
                                                },
                                            }}
                                        />

                                        <Box sx={{display: "flex", justifyContent: "center", gap:"10px"}}>
                                            <Typography variant="body1" sx={{cursor: "pointer", ":hover": {textDecoration: "underline"}}} onClick={saveChangedChannelName}>Save</Typography>
                                            <Typography variant="body1" sx={{cursor: "pointer", ":hover": {textDecoration: "underline"}}} onClick={() => changeIsEditingChannel(false)}>Cancel</Typography>
                                        </Box>
                                    </Box>
                                }
                            </Box>
                        </Box> :
                        <></>
                    }
                </Box>
            </Box> :
            <Box></Box>
        )
    }
}