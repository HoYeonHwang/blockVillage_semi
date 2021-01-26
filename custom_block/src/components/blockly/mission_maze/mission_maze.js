import styles from './mission_maze.module.css';
import '../all_blocks/start_blocks';
import '../all_blocks/judgment_blocks';
import '../all_blocks/flow_block';
import '../all_blocks/calculation_block';
import React, { useState } from 'react';
import ReactBlockly from 'react-blockly';
import Blockly from 'blockly';

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
      name: '판단',
      colour: '#bb8137',
      blocks: [
        {
          type: 'block_judgment_equals',
        },
        {
          type: 'block_judgment_strictinequality_left',
        },
        {
          type: 'block_judgment_strictinequality_right',
        },
        {
          type: 'block_judgment_notequal',
        },
        {
          type: 'block_judgment_strictinequality_leftequal',
        },
        {
          type: 'block_judgment_strictinequality_rightequal',
        },
        {
          type: 'block_judgment_compare_and',
        },
        {
          type: 'block_judgment_compare_or',
        },
        {
          type: 'block_judgment_compare_not',
        },
      ],
    },
    {
      name: '움직임',
      colour: '#5CA699',
      blocks: [
        {
          type: 'move_x',
        },
      ],
    },
    {
      name: '흐름',
      colour: '200',
      blocks: [
        {
          type: 'repeat_times'
        },
        {
          type: 'repeat'
        },
        {
          type: 'repeat_condition'
        },
        {
          type: 'break'
        },
        {
          type: 'condition'
        },
        {
          type: 'if_else'
        }
      ]
    },
  ]);
  function workspaceDidChange(workspace) {
    // save 형태
    setInitialXml(Blockly.Xml.domToText(Blockly.Xml.workspaceToDom(workspace)));
    // document.getElementById('generated-xml').innerText = newXml;

    // playground 형태로 예상 중
    setJavascript(Blockly.JavaScript.workspaceToCode(workspace));
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
    setModal(!modal);
  };

  return (
    <section className={styles.page_style}>
      <button onClick={statusModal}>dfs</button>
      <BlocklyNavbar />
      <div className={styles.container}>
        {modal && (
          <Draggable onStart={onStart} onStop={onStop} bounds="parent">
            <div className={styles.playground}>
              <div className={styles.headerPlayGround}></div>
              <PlayGround javascript_code={javascript} />
            </div>
          </Draggable>
        )}
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
  );
}