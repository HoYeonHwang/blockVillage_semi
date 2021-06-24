import styles from "./mission_maze.module.css"
import '../all_blocks/start_blocks'
import '../all_blocks/movement_blocks'
import React, {useState} from 'react'
import ReactBlockly from 'react-blockly'
import Blockly from 'blockly'

import Draggable from 'react-draggable';

import PlayGround from '../play_ground/play_ground';
import BlocklyNavbar from '../blockly_navbar/blockly_navbar';

export default function MissionMaze() {
  const [activeDrags, setActiveDrags] = useState(0);

  const [modal, setModal] = useState(true);
  const [javascript, setJavascript] = useState();
  const [initialXml, setInitialXml] = useState(
    '<xml xmlns="http://www.w3.org/1999/xhtml"></xml>'
  );
  const [toolboxCategories, setToolboxCategories] = useState([
    {
      name: '시작',
      colour: '#5C81A6',
      blocks: [
        {
          type: 'start_button'
        },
        {
          type: 'end_button'
        },
      ]
    },
    {
      name: '움직임',
      colour: '#5CA699',
      blocks: [
        {
          type: 'move_forward'
        },
        {
          type: 'move_turnleft'
        },
        {
          type: 'move_turnright'
        },
      ]
    }
  ])
  function workspaceDidChange(workspace) {
    // save 형태
    setInitialXml(Blockly.Xml.domToText(Blockly.Xml.workspaceToDom(workspace)))
    // document.getElementById('generated-xml').innerText = newXml;

    // playground 형태로 예상 중
    setJavascript(Blockly.JavaScript.workspaceToCode(workspace))
    console.log(javascript);
    // document.getElementById('code').value = code;
  }

  const onStart = () => {
    setActiveDrags(activeDrags + 1);
  };

  const onStop = () => {
    setActiveDrags(activeDrags - 1);
  };

  const statusModal = () => {
    setModal(!modal)
  };

  return (
    <section className={styles.page_style}>
      <button onClick={statusModal}>dfs</button>
      <BlocklyNavbar />
      <div className={styles.container}>
        {modal && 
          <Draggable
            onStart={onStart}
            onStop={onStop}
            bounds="parent"
            >
            <div className={styles.playground}>
              <div className={styles.headerPlayGround}></div>
              <PlayGround 
                javascript_code={javascript} />
            </div>
          </Draggable>
        }
        <div className={styles.workspace_cata}>
          <div className={styles.workspace}>
            <ReactBlockly
              toolboxCategories={toolboxCategories}
              initialXml={initialXml}
              wrapperDivClassName={styles.fill_height}
              workspaceConfiguration={{
                grid: {
                  spacing: 20,
                  length: 3,
                  colour: '#',
                  snap: true,
                },
              }}
              workspaceDidChange={workspaceDidChange}
            />
          </div>
        </div>
      </div>
    </section>
  )
}
