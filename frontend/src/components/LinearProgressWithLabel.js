import {Box} from "@mui/system";
import {LinearProgress} from "@mui/material";
import Typography from "@mui/material/Typography";

const LinearProgressWithLabel = (props) => {
  const {value, fileName} = props;
  return (
    <Box sx={{display: 'flex', flexDirection: 'column', justifyContent: 'stretch'}}>
      <Box sx={{display: 'flex', justifyContent: 'space-between'}}>
        <Typography variant="body2" color="text.secondary">
          {fileName}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          {`${Math.round(value)}%`}
        </Typography>
      </Box>
      <Box sx={{mt: 1}}>
        <LinearProgress variant="determinate" value={value}/>
      </Box>
    </Box>
  );
}

export default LinearProgressWithLabel;