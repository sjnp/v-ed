import {Input} from "@mui/material";
import Button from "@mui/material/Button";
import {useState} from "react";
import axios from "axios";

const TestUpload = () => {

  const [chunks, setChunks] = useState([]);

  const handleFile = (event) => {
    const file = event.target.files[0];
    const chunkSize = 5 * 1024 * 1024;
    const newChunks = []
    for (let start = 0; start < file.size; start += chunkSize) {
      const chunk = file.slice(start, start + chunkSize);
      newChunks.push(chunk);
    }
    setChunks(newChunks);
  }
  const showChunks = () => {
    console.log(chunks);
  }

  const handleUpload = async () => {
    // for (let i = 0; i < chunks.length; i++) {
    //     await axios.put(
    //       `https://objectstorage.ap-singapore-1.oraclecloud.com/p/4qwii3ax1_78OOdTxIacP46gxSSA2hXeslfF26R0xWUjhGb_nXPOyuwfxaN4jfjf/n/axjskktj5xlm/b/bucket-20220310-1506/u/operationStrix01.mkv/id/91c996d8-948b-f7a0-5978-28f2af850bc3/${i+1}`,
    //       chunks[i])
    //       .then(response => console.log(`success ${i+1}`))
    //       .catch(err => console.error(err))
    // }

    let promises = [];
    for (let i = 0; i < chunks.length; i++) {
      promises.push(
        axios.put(
          `https://objectstorage.ap-singapore-1.oraclecloud.com/p/XXvFzOhvxFRZFa-XPjnT-ueEIJx-m2F1HoAuYzhE90EaetI_xgn44BY-FE3lun1w/n/axjskktj5xlm/b/bucket-20220310-1506/u/bunny.mp4/id/9c9e2846-dd97-d92c-53cd-0fb23ab143d4/${i+1}`,
          chunks[i])
          .then(response => console.log(response))
          .catch(err => console.error(err))
      )
    }
    Promise.all(promises).then(() => console.log("complete"));

    // axios.put(
    //   `https://objectstorage.ap-singapore-1.oraclecloud.com/p/4qwii3ax1_78OOdTxIacP46gxSSA2hXeslfF26R0xWUjhGb_nXPOyuwfxaN4jfjf/n/axjskktj5xlm/b/bucket-20220310-1506/u/operationStrix01.mkv/id/4e738756-78f0-fe40-5826-204f39663e0d/1`,
    //   chunks[0], {encoding: null})
    //   .then(response => console.log(response))
    //   .catch(err => console.error(err))
  }
  //

  return (
    <>
      <label htmlFor="contained-button-file">
        <Input sx={{display: 'none'}} accept="video/*, .mkv" id="contained-button-file" type="file"
               onChange={handleFile}/>
        <Button variant="contained" component="span">
          Upload
        </Button>
      </label>
      <Button variant="contained" component="span" onClick={showChunks}>
        Show chunks
      </Button>
      <Button variant="contained" component="span" onClick={handleUpload}>
        Go!
      </Button>
    </>
  )
}

export default TestUpload;