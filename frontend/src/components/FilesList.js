import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  List,
  ListItem,
  ListItemText,
  ListItemIcon,
  Avatar,
} from "@mui/material";
import InsertDriveFileIcon from "@mui/icons-material/InsertDriveFile";
import { messages, endpoints } from "../constants";

function FilesList() {
  const [files, setFiles] = useState([]);

  useEffect(() => {
    const fetchFiles = async () => {
      try {
        const response = await axios.get(endpoints.filesUrl);
        setFiles(response.data);
      } catch (error) {
        console.error(messages.fetchingError, error);
      }
    };

    fetchFiles();
  }, []);

  return (
    <List>
      {files.map((file) => (
        <ListItem
          button
          key={file.id}
          onClick={() => window.open(endpoints.downloadUrl(file.id), "_blank")}
        >
          <ListItemIcon>
            <Avatar>
              <InsertDriveFileIcon />
            </Avatar>
          </ListItemIcon>
          <ListItemText
            primary={file.name}
            secondary={
              `Size: ${file.size} KB` /* Assuming size is part of the file metadata */
            }
          />
        </ListItem>
      ))}
    </List>
  );
}

export default FilesList;
